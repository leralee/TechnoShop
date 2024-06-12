package com.dns.category;

import com.dns.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository repo;

    @Test
    public void testListEnabledCategories() {
        List<Category> categories = repo.findAllEnabled();
        categories.forEach(category -> System.out.println(category.getName() + " (" + category.isEnabled() + ")"));

    }

    @Test
    public void testFindCategoryByAlias() {
        String alias = "компьютеры";
        Category category = repo.findByAliasAndEnabledTrue(alias);

        assertThat(category).isNotNull();
    }
}
