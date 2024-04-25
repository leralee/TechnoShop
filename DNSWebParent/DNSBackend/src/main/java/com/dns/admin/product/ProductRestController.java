package com.dns.admin.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductRestController {

    private final ProductService service;

    @Autowired
    public ProductRestController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/products/check_unique")
    public String checkUnique(@RequestParam(value = "id", required = false) Integer id, @RequestParam("name") String name) {
        return service.checkUnique(id, name);
    }

}
