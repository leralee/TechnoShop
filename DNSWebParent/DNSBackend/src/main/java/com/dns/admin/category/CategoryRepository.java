package com.dns.admin.category;

import com.dns.common.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends ListCrudRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    List<Category> findRootCategories(Sort sort);

    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    Page<Category> findRootCategories(Pageable sort);

    @Query("SELECT c FROM Category c WHERE c.name LIKE %:keyword%")
    Page<Category> search(String keyword, Pageable pageable);

    Category findByName(String name);

    Category findByAlias(String alias);

    @Query("Update Category c Set c.enabled = (:enabled) where c.id=(:id)")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    Long countById(Integer id);
}
