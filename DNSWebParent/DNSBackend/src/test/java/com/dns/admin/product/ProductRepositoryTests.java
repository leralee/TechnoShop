package com.dns.admin.product;

import com.dns.common.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateProduct() {
        Brand brand = entityManager.find(Brand.class, 6);
        Category category = entityManager.find(Category.class, 1);

        Product product = new Product();
        product.setName("Apple Watch Series 9");
        product.setAlias("apple_watch_series_9");
        product.setShortDescription("Смарт-часы Apple Watch. Материал корпуса — алюминий. Время работы в активном " +
                "режиме — до 18 часов. Функция Double Tap активируется посредством двух быстрых касаний указательного" +
                " и большого пальцев — можно ответить на звонок, выключить будильник и управлять воспроизведением" +
                " музыки. Объем встроенной памяти — 64 Гб. В комплекте — кабель USB?C с магнитным креплением для" +
                " быстрой зарядки");


        product.setBrand(brand);
        product.setCategory(category);
        product.setPrice(46070);
        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = repo.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllProducts() {
        List<Product> listProducts  = repo.findAll();
        listProducts.forEach(System.out::println);
    }

    @Test
    public void testGetProductById() {
        Optional<Product> me = repo.findById(1);
        assertThat(me).isNotEmpty();
        System.out.println(me.get());
    }

    @Test
    public void testUpdateProduct() {
        Integer id = 1;
        Product product = repo.findById(id).get();
        product.setPrice(100200);

        repo.save(product);

        Product updatedProduct = entityManager.find(Product.class, id);

        assertThat(updatedProduct.getPrice()).isEqualTo(100200);
    }

    @Test
    public void testDeleteProduct() {
        Integer id = 2;
        repo.deleteById(id);

        Optional<Product> result = repo.findById(id);

        assertThat(result.isEmpty());
    }

    @Test
    public void testSaveProductWithImages() {
        Integer productId = 5;
        Product product = repo.findById(productId).get();

        product.setMainImage("main image.jpg");
        product.addExtraImages("extra image 1.png");
        product.addExtraImages("extra image 2.png");
        product.addExtraImages("extra image 3.png");

        Product savedProduct = repo.save(product);

        assertThat(savedProduct.getImages()).hasSize(3)
                .allMatch(image -> image != null && image.getName() != null);
    }

    @Test
    public void testSaveProductWithDetails() {
        Integer productId = 1;
        Product product = repo.findById(productId).get();
        product.addDetails("detail 1", "value 1");
        product.addDetails("detail 2", "value 2");
        product.addDetails("detail 3", "value 3");

        Product savedProduct = repo.save(product);
        assertThat(savedProduct.getDetails()).isNotEmpty();
    }
}
