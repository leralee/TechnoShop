package com.dns.admin.product;

import com.dns.admin.FileUploadedUtil;
import com.dns.admin.brand.BrandService;
import com.dns.admin.category.CategoryService;
import com.dns.admin.security.DnsUserDetails;
import com.dns.common.entity.Brand;
import com.dns.common.entity.Category;
import com.dns.common.entity.Product;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;

@Controller
@Transactional
public class ProductController {

    final ProductService productService;
    final BrandService brandService;
    final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService service, BrandService brandService, CategoryService categoryService) {
        this.productService = service;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products")
    public String listFirstPage(Model model) {
        return listByPage(1, "name", "asc", null, 0, model);
    }

    @GetMapping("/products/page/{pageNum}")
    public String listByPage(@PathVariable("pageNum") int pageNum,
                             @RequestParam(value = "sortField", required = false) String sortField,
                             @RequestParam(value = "sortDir") String sortDir,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "categoryId", required = false) Integer categoryId,
                             Model model) {

        Page<Product> page = productService.listByPage(pageNum, sortField, sortDir, keyword, categoryId);
        List<Product> listProducts = page.getContent();

        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        long startCount = (long) (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
        long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        if (categoryId != null) {model.addAttribute("categoryId", categoryId);}

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);

        return "products/products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        List<Brand> listBrands = brandService.listAll();

        Product product = new Product();
        product.setEnabled(true);
        product.setInStock(true);

        model.addAttribute("product", product);
        model.addAttribute("listBrands", listBrands);
        model.addAttribute("pageTitle", "Создание нового продукта");
        model.addAttribute("numberOfExistingExtraImages", 0);

        return "products/product_form";
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product, RedirectAttributes redirectAttributes,
                              @RequestParam(value = "fileImage", required = false) MultipartFile mainImageMultipart,
                              @RequestParam(value = "extraImage", required = false) MultipartFile[] extraImageMultiparts,
                              @RequestParam(name = "detailIDs", required = false) String[] detailsIDs,
                              @RequestParam(name = "detailNames", required = false) String[] detailNames,
                              @RequestParam(name = "detailValues", required = false) String[] detailValues,
                              @RequestParam(name = "imageIDs", required = false) String[] imageIDs,
                              @RequestParam(name = "imageNames", required = false) String[] imageNames,
                              @AuthenticationPrincipal DnsUserDetails loggedUser)
            throws IOException {
        if (loggedUser.hasRole("Продавец")) {
            productService.saveProductPrice(product);
            redirectAttributes.addFlashAttribute("message", "Товар успешно сохранен");
            return "redirect:/products";
        }
        ProductSaveHelper.setMainImageName(mainImageMultipart, product);
        ProductSaveHelper.setExistingExtraImageNames(imageIDs, imageNames, product);
        ProductSaveHelper.setNewExtraImageNames(extraImageMultiparts, product);
        ProductSaveHelper.setProductDetails(detailsIDs, detailNames, detailValues, product);

        Product savedProduct = productService.save(product);

        ProductSaveHelper.saveUploadedImages(mainImageMultipart, extraImageMultiparts, savedProduct);
        ProductSaveHelper.deleteExtraImagesWereRemovedOnForm(product);


        redirectAttributes.addFlashAttribute("message", "Товар успешно сохранен");
        return "redirect:/products";
    }



    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            productService.delete(id);
            String productExtraImagesDir = "../product-images/" + id + "extras";
            String productImagesDir = "../product-images/" + id;

            FileUploadedUtil.removeDir(productExtraImagesDir);
            FileUploadedUtil.removeDir(productImagesDir);

            redirectAttributes.addFlashAttribute("message",
                    "Категория с ID: " + id + " удалена");
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editCategory(@PathVariable(name = "id") Integer id, Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.get(id);
            List<Brand> listBrands = brandService.listAll();
            Integer numberOfExistingExtraImages = product.getImages().size();

            model.addAttribute("product", product);
            model.addAttribute("listBrands", listBrands);
            model.addAttribute("numberOfExistingExtraImages", numberOfExistingExtraImages);
            model.addAttribute("pageTitle", "Редактирование товара (ID: " + id + ")");

            return "products/product_form";
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("nessage", ex.getMessage());
            return "redirect:/products";
        }
    }

    @GetMapping("/products/detail/{id}")
    public String viewProductDetails(@PathVariable(name = "id") Integer id, Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.get(id);

            model.addAttribute("product", product);

            return "products/product_detail_modal";
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("nessage", ex.getMessage());
            return "redirect:/products";
        }
    }

    @GetMapping("/products/{id}/enabled/{status}")
    public String updateCategoryEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
                                              RedirectAttributes redirectAttributes) {
        productService.updateProductEnabledStatus(id, enabled);
        String status = enabled ? " включена" : " отключена";
        String message = "Товар с  ID: " + id + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/products";
    }
}
