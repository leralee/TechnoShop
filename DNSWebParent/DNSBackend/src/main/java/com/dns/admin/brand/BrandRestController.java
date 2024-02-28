package com.dns.admin.brand;

import com.dns.admin.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandRestController {

    private final BrandService service;

    @Autowired
    public BrandRestController(BrandService service) {
        this.service = service;
    }

    @PostMapping("/brands/check_unique")
    public String checkUnique(@RequestParam(value = "id", required = false) Integer id, @RequestParam("name") String name) {
        return service.checkUnique(id, name);
    }

}
