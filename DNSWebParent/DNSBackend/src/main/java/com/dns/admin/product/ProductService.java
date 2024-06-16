package com.dns.admin.product;

import com.dns.common.entity.Brand;
import com.dns.common.entity.Product;
import com.dns.common.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ProductService {

    public static final int PRODUCTS_PER_PAGE = 5;
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public Page<Product> listByPage(int pageNum, String sortField, String sortDir, String keyword, Integer categoryId) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE, sort);

        if (keyword != null && !keyword.isEmpty()) {
            if (categoryId != null && categoryId != 0) {
                String categoryIdMatch = "-" + categoryId + "-";
                return repo.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
            }
            return repo.findAll(keyword, pageable);
        }
        if (categoryId != null && categoryId != 0) {
            String categoryIdMatch = "-" + categoryId + "-";
            return repo.findAllInCategory(categoryId, categoryIdMatch, pageable);
        }
        return repo.findAll(pageable);
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setCreatedTime(new Date());
        }
        if (product.getAlias() == null || product.getAlias().isEmpty()) {
            String defaultAlias = product.getName().replace(" ", "-");
            product.setAlias(defaultAlias);
        } else {
            product.setAlias(product.getAlias().replace(" ", "-"));
        }

        product.setUpdatedTime(new Date());
        return repo.save(product);
    }

    public void saveProductPrice(Product productInForm) {
        Product productInDB = repo.findById(productInForm.getId()).get();
        productInDB.setCost(productInForm.getCost());
        productInDB.setPrice(productInForm.getPrice());
        productInDB.setDiscountPercent(productInForm.getDiscountPercent());

        repo.save(productInDB);
    }

    public String checkUnique(Integer id, String name) {
        boolean isCreatingNew = (id==null || id == 0);
        Product product = repo.findByName(name);
        if (isCreatingNew) {
            if (product != null) {
                return "Duplicated";
            }
        } else {
            if (product != null && !Objects.equals(product.getId(), id)) {
                return "Duplicated";
            }
        }
        return "OK";
    }

    public void updateProductEnabledStatus(Integer id, boolean enabled) {
        repo.updateEnabledStatus(id, enabled);
    }

    public void delete(Integer id) throws ProductNotFoundException {
        Long countById = repo.countById(id);
        if (countById == null || countById == 0) {
            throw new ProductNotFoundException("Товар с ID: " + id + " не найден");
        }
        repo.deleteById(id);
    }

    public Product get(Integer id) throws ProductNotFoundException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new ProductNotFoundException("Категория с ID: " + id + " не найдена");
        }
    }
}
