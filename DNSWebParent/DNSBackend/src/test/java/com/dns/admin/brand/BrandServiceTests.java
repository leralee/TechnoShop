package com.dns.admin.brand;

import com.dns.common.entity.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class BrandServiceTests {

    @Mock
    private BrandRepository repo;

    @InjectMocks
    private BrandService service;


    @Test
    public void testCheckUniqueInNewModeReturnDuplicated() {
        Integer id = null;
        String name = "Asus";
        Brand brand = new Brand(name);

        Mockito.when(repo.findByName(name)).thenReturn(brand);

        String result = service.checkUnique(id, name);
        assertThat(result).isEqualTo("Duplicated");
    }


    @Test
    public void testCheckUniqueInNewModeReturnOk() {
        Integer id = null;
        String name = "New brand";

        Mockito.when(repo.findByName(name)).thenReturn(null);

        String result = service.checkUnique(id, name);
        assertThat(result).isEqualTo("OK");
    }

    @Test
    public void testCheckUniqueInEditModeReturnDuplicated() {
        String name = "Asus";
        Brand brand = new Brand(1, name);

        Mockito.when(repo.findByName(name)).thenReturn(brand);

        String result = service.checkUnique(2, name);
        assertThat(result).isEqualTo("Duplicated");
    }

    @Test
    public void testCheckUniqueInEditModeReturnOk() {
        Integer id = 1;
        String name = "Asus";
        Brand brand = new Brand(id, name);

        Mockito.lenient().when(repo.findByName(name)).thenReturn(brand);

        String result = service.checkUnique(id, "Asus new");
        assertThat(result).isEqualTo("OK");
    }
}

