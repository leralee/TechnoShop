package com.dns.admin.category;

import com.dns.common.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    List<Category> findRootCategories();

    Category findByName(String name);

    Category findByAlias(String alias);
    @Query("SELECT u FROM Category u WHERE u.name LIKE %:keyword%")
    Page<Category> findAll(String keyword, Pageable pageable);
}
