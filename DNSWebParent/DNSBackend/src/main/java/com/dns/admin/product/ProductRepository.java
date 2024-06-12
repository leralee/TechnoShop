package com.dns.admin.product;

import com.dns.common.entity.Brand;
import com.dns.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {

    Product findByName(String name);

    @Query("Update Product p Set p.enabled = (:enabled) where p.id=(:id)")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    Long countById(Integer id);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% " +
            "OR p.shortDescription LIKE %?1%" +
            "OR p.brand.name LIKE %?1% " +
            "OR p.category.name LIKE %?1%")
    Page<Product> findAll(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 " +
            "OR p.category.allParentIDs LIKE %?2%")
    Page<Product> findAllInCategory(Integer categoryId, String categoryIdMatch, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (p.category.id = ?1 OR p.category.allParentIDs LIKE %?2%) " +
            "AND (p.name LIKE %?3% OR p.shortDescription LIKE %?3% " +
            "OR p.brand.name LIKE %?3% OR p.category.name LIKE %?3%)")
    Page<Product> searchInCategory(Integer categoryId, String categoryIdMatch, String keyword, Pageable pageable);
}
