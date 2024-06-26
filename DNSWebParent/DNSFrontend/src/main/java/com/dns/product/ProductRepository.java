package com.dns.product;

import com.dns.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author valeriali
 * @project TechnoShopProject
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.enabled = true "
            + "AND (p.category.id = ?1 OR p.category.allParentIDs LIKE %?2%)"
            + "ORDER BY p.name ASC")
    Page<Product>listByCategory(Integer categoryId, String categoryIDMatch, Pageable pageable);

    Product findByAlias(String alias);
}
