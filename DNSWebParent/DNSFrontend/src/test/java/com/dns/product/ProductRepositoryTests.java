package com.dns.product;

import com.dns.common.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author valeriali on {16.06.2024}
 * @project TechnoShopProject
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {

    @Autowired
    ProductRepository repo;

    @Test
    public void testFindByAlias() {
        String alias = "samsung_galaxy_s22";
        Product product = repo.findByAlias(alias);

        assertThat(product).isNotNull();
    }

}
