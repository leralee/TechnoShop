package com.dns.product;

import com.dns.category.CategoryService;
import com.dns.common.entity.Category;
import com.dns.common.entity.Product;
import com.dns.common.exception.CategoryNotFoundException;
import com.dns.common.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author valeriali
 * @project TechnoShopProject
 */

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/c/{category_alias}")
    public String viewCategoryFirstPage(@PathVariable("category_alias") String alias, Model model) {
        return viewCategoryByPage(alias, 1, model);
    }

    @GetMapping("/c/{category_alias}/page/{pageNum}")
    public String viewCategoryByPage(@PathVariable("category_alias") String alias,
                                     @PathVariable("pageNum") int pageNum, Model model) {
        try {
            Category category = categoryService.getCategory(alias);
            List<Category> listCategoryParents = categoryService.getCategoryParent(category);

            Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());
            List<Product> listProducts = pageProducts.getContent();

            long startCount = (long) (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
            long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
            if (endCount > pageProducts.getTotalElements()) {
                endCount = pageProducts.getTotalElements();
            }

            model.addAttribute("currentPage", pageNum);
            model.addAttribute("startCount", startCount);
            model.addAttribute("endCount", endCount);
            model.addAttribute("totalItems", pageProducts.getTotalElements());
            model.addAttribute("totalPages", pageProducts.getTotalPages());
            model.addAttribute("pageTitle", category.getName());
            model.addAttribute("listCategoryParents", listCategoryParents);
            model.addAttribute("listProducts", listProducts);
            model.addAttribute("category", category);

            return "product/products_by_category";
        } catch (CategoryNotFoundException ex) {
            return "error/404";
        }
    }

    @GetMapping("/p/{product_alias}")
    public String viewProductDetail(@PathVariable("product_alias") String alias, Model model) {

        try {
            Product product = productService.getProduct(alias);
            List<Category> listCategoryParents = categoryService.getCategoryParent(product.getCategory());

            model.addAttribute("listCategoryParents", listCategoryParents);
            model.addAttribute("product", product);
            model.addAttribute("pageTitle", product.getShortName());

            return "product/product_detail";
        } catch (ProductNotFoundException ex) {
            return "error/404";
        }
    }

    @GetMapping("/search")
    public String searchFirstPage(@RequestParam("keyword") String keyword,  Model model) {
        return searchByPage(keyword, 1, model);
    }

    @GetMapping("/search/page/{pageNum}")
    public String searchByPage(@RequestParam("keyword") String keyword, @PathVariable("pageNum") int pageNum,  Model model) {
        Page<Product> pageProducts = productService.search(keyword, pageNum);
        List<Product> listResult = pageProducts.getContent();


        long startCount = (long) (pageNum - 1) * ProductService.SEARCH_RESULT_PER_PAGE + 1;
        long endCount = startCount + ProductService.SEARCH_RESULT_PER_PAGE - 1;
        if (endCount > pageProducts.getTotalElements()) {
            endCount = pageProducts.getTotalElements();
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", pageProducts.getTotalElements());
        model.addAttribute("totalPages", pageProducts.getTotalPages());
        model.addAttribute("pageTitle", keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("listResult", listResult);

        return "product/search_result";
    }


}
