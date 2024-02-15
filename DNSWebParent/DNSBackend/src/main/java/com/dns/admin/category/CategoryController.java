package com.dns.admin.category;

import com.dns.admin.FileUploadedUtil;
import com.dns.admin.user.UserNotFoundException;
import com.dns.common.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author valeriali on {05.02.2024}
 * @project TechnoShopProject
 */
@Controller
public class CategoryController {

    final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/categories")
    public String listFirstPage(Model model) {
        List<Category> listCategories = service.listAll();
        model.addAttribute("listCategories", listCategories);
        return "categories/categories";
//        return listByPage(1, model, "name", "asc", null);
    }

    @GetMapping("/categories/page/{pageNum}")
    public String listByPage(@PathVariable("pageNum") int pageNum, Model model,
                             @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir,
                             @RequestParam("keyword") String keyword
    ) {
        Page<Category> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<Category> listCategories = page.getContent();

        long startCount = (long) (pageNum - 1) * CategoryService.CATEGORIES_PER_PAGE + 1;
        long endCount = startCount + CategoryService.CATEGORIES_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "categories/categories";
    }

    @GetMapping("categories/new")
    public String newCategory(Model model) {
        List<Category> listCategories = service.listCategoriesUsedInForm();
        model.addAttribute("category", new Category());
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("pageTitle", "Создание новой категории");
        return "categories/category_form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(Category category, @RequestParam("fileImage") MultipartFile multipartFile,
                               RedirectAttributes redirectAttributes) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            category.setImage(fileName);
            Category savedCategory = service.save(category);
            String uploadDir = "../category-images/" + savedCategory.getId();
            FileUploadedUtil.cleanDir(uploadDir);
            FileUploadedUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            service.save(category);
        }
        redirectAttributes.addFlashAttribute("message", "Категория успешно сохранена");
        return "redirect:/categories";
    }

    @GetMapping("categories/edit/{id}")
    public String editCategory(@PathVariable(name = "id") Integer id, Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            Category category = service.get(id);
            List<Category> listCategories = service.listCategoriesUsedInForm();
            model.addAttribute("category", category);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Редактирование категории (ID: " + id + ")");

            return "categories/category_form";
        } catch (CategoryNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/categories";
        }
    }

    @GetMapping("categories/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, Model model,
                             RedirectAttributes redirectAttributes) {
//        try {
//            service.delete(id);
//            redirectAttributes.addFlashAttribute("message",
//                    "Пользователь с ID: " + id + " удален");
//        } catch (UserNotFoundException ex) {
//            redirectAttributes.addFlashAttribute("message", ex.getMessage());
//        }
        return "redirect:/categories";

    }

}
