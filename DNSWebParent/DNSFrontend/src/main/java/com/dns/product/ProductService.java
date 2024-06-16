package com.dns.product;

import com.dns.common.entity.Product;
import com.dns.common.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author valeriali on {09.06.2024}
 * @project TechnoShopProject
 */

@Service
public class ProductService {

    public static final int PRODUCTS_PER_PAGE = 10;

    private final ProductRepository repo;

    @Autowired
    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public Page<Product> listByCategory(int pageNum, Integer categoryId) {
        String categoryIdMatch = "-" + categoryId + "-";
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

        return repo.listByCategory(categoryId, categoryIdMatch, pageable);
    }

    public Product getProduct(String alias) throws ProductNotFoundException {
        Product product = repo.findByAlias(alias);
        if (product == null) {
            throw new ProductNotFoundException("Товар с алиасом: " + alias + " не найден");
        }
        return product;
    }
}
