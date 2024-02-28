package com.dns.admin.brand;

import com.dns.common.entity.Brand;
import com.dns.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class BrandRepositoryTests {

    @Autowired
    private BrandRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateBrand1() {
        Category laptops = new Category(4);
        Brand acer = new Brand("Acer");
        acer.addCategory(laptops);

        Brand savedBrand = repo.save(acer);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateBrand2() {
        Category smartphone = new Category(7);
        Category tablets = new Category(25);
        Brand apple = new Brand("Apple");
        apple.addCategory(smartphone);
        apple.addCategory(tablets);

        Brand savedBrand = repo.save(apple);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateBrand3() {
        Category memory = new Category(8);
        Category hardDrive = new Category(26);
        Brand samsung = new Brand("Samsung");
        samsung.addCategory(memory);
        samsung.addCategory(hardDrive);

        Brand savedBrand = repo.save(samsung);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindAllBrands() {
        List<Brand> brands  = repo.findAll();
        brands.forEach(System.out::println);

        assertThat(brands).isNotEmpty();
    }

    @Test
    public void testGetBrandById() {
        Brand brand = repo.findById(1).get();
        assertThat(brand.getName()).isEqualTo("Acer");
    }

    @Test
    public void testUpdateBrand() {
        String newName = "Acer_new";
        Brand brand = repo.findById(1).get();
        brand.setName("Acer_new");
        Brand savedBrand = repo.save(brand);
        assertThat(brand.getName()).isEqualTo(newName);
    }


    @Test
    public void testDeleteBrand() {
        Integer brandId = 1;
        repo.deleteById(brandId);
        Optional<Brand> result = repo.findById(brandId);
        assertThat(result.isEmpty());
    }
//
//    @Test
//    public void testGetUserByEmail() {
//        String email = "anna14@gmail.com";
//        User user = repo.findByEmail(email);
//
//        assertThat(user).isNotNull();
//    }
//
//    @Test
//    public void testDisableUser() {
//        Integer id = 1;
//        repo.updateEnabledStatus(id, false);
//    }
//
//    @Test
//    public void testEnableUser() {
//        Integer id = 3;
//        repo.updateEnabledStatus(id, true);
//    }
//
//    @Test
//    public void testListFirstPage() {
//        int pageNumber = 0;
//        int pageSize = 4;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<User> page = repo.findAll(pageable);
//
//        List<User> listUsers = page.getContent();
//        listUsers.forEach((System.out::println));
//
//        assertThat(listUsers.size()).isEqualTo(pageSize);
//    }
//
//    @Test
//    public void testSearchUsers() {
//        String keyword = "Tom";
//
//        int pageNumber = 0;
//        int pageSize = 4;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<User> page = repo.findAll(keyword, pageable);
//
//        List<User> listUsers = page.getContent();
//        listUsers.forEach(System.out::println);
//
//        assertThat(listUsers.size()).isGreaterThan(0);
//
//    }
}
