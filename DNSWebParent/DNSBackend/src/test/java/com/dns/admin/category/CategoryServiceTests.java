package com.dns.admin.category;

import com.dns.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    @Mock
    private CategoryRepository repo;

    @InjectMocks
    private CategoryService service;


    @Test
    public void testCheckUniqueInNewModeReturnDuplicateName() {
        Integer id = null;
        String name = "Компьютеры";
        String alias = "abc";

        Category category = new Category(id, name, alias);
        Mockito.when(repo.findByName(name)).thenReturn(category);
        Mockito.lenient().when(repo.findByAlias(alias)).thenReturn(null);
        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("DuplicateName");
    }
}

