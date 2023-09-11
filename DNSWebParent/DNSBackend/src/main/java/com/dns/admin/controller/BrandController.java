//package com.example.techno_shop.controller;
//
//
//import com.example.techno_shop.exceptions.BrandNotFoundException;
//import com.example.techno_shop.model.Brand;
//import com.example.techno_shop.model.Category;
//import com.example.techno_shop.service.BrandService;
//import com.example.techno_shop.service.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.IOException;
//import java.util.List;
//
//
//
// @Controller
//public class BrandController {
//    BrandService brandSerive;
//
//    CategoryService categoryService;
//
//    @Autowired
//    public BrandController(BrandService brandSerive, CategoryService categoryService){
//        this.brandSerive = brandSerive;
//        this.categoryService = categoryService;
//    }
//
//    @GetMapping("/brands")
//    public String listAll(@Param("sortDir") String sortDir,
//                          @Param("keyword") String keyword, Model model) {
//        if (sortDir == null || sortDir.isEmpty()) {
//            sortDir = "asc";
//        }
//        List<Brand> listBrands = brandSerive.listAll(sortDir, keyword);
//        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
//
//        model.addAttribute("listBrands", listBrands);
//        model.addAttribute("reverseSortDir", reverseSortDir);
//        model.addAttribute("pageTitle", "Бренд");
//
//        return "brands/brands";
//    }
//
//    @GetMapping("/brands/new")
//    public String newBrand(Model model) {
//        List <Category> listCategories = categoryService.listCategoriesUsedInForm();
//        model.addAttribute("listCategories", listCategories);
//        model.addAttribute("brand", new Brand());
//        model.addAttribute("pageTitle", "Добавление нового бренда");
//
//        return "brands/brand_form";
//    }
//
//    @PostMapping("/brands/save")
//    public String saveBrand(Brand brand, RedirectAttributes redirectAttributes) {
//        brandSerive.save(brand);
//        redirectAttributes.addFlashAttribute("message", "Бренд сохранен");
//        return "redirect:/brands";
//    }
//
//
//    @GetMapping("/brands/edit/{id}")
//    public String editBrand(@PathVariable(name = "id") Integer id, Model model,
//                            RedirectAttributes redirectAttributes){
//        try {
//            Brand brand = brandSerive.get(id);
//            List<Category> listCategories = categoryService.listCategoriesUsedInForm();
//
//            model.addAttribute("listCategories", listCategories);
//            model.addAttribute("brand", brand);
//            model.addAttribute("pageTitle", "Редактирование (ID: " + id + ")");
//
//            return "brands/brand_form";
//        } catch (BrandNotFoundException ex) {
//            redirectAttributes.addFlashAttribute("message", ex.getMessage());
//            return "redirect:/brands";
//        }
//    }
//
//    @GetMapping("/brands/delete/{id}")
//    public String deleteBrand(@PathVariable(name = "id") Integer id,
//                            RedirectAttributes redirectAttributes){
//        try {
//            brandSerive.delete(id);
//            redirectAttributes.addFlashAttribute("message", "Бренд с ID: " + id + " успешно удален");
//
//        } catch (BrandNotFoundException ex) {
//            redirectAttributes.addFlashAttribute("message", ex.getMessage());
//
//        }
//        return "redirect:/brands";
//    }
//
//}
//
//
