package com.dns;

import com.dns.category.CategoryService;
import com.dns.common.entity.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author valeriali
 * @project TechnoShopProject
 */
@Controller
public class MainController {

    private final CategoryService categoryService;

    public MainController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String viewHome(Model model) {
        List<Category> listCategories = categoryService.listNoChildrenCategories();
        model.addAttribute("listCategories", listCategories);

        return "index";
    }


}
