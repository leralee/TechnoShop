package com.dns.admin.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class CategoryRestController {

    private final CategoryService service;

    @Autowired
    public CategoryRestController(CategoryService service) {
        this.service = service;
    }

    @PostMapping("/categories/check_unique")
    public String checkUnique(@RequestParam(value = "id", required = false) Integer id, @RequestParam("name") String name,
                              @RequestParam("alias") String alias) {
        return service.checkUnique(id, name, alias);
    }

}
