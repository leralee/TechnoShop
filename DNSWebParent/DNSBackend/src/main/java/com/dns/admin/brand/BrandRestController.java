package com.dns.admin.brand;

import com.dns.common.entity.Brand;
import com.dns.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/brands/{id}/categories")
    public List<CategoryDTO> listCategoriesByBrand(@PathVariable(name = "id") Integer brandId) throws BrandNotFoundRestException {
        List<CategoryDTO> listCategories = new ArrayList<>();

        try {
            Brand brand = service.get(brandId);
            Set<Category> categories = brand.getCategories();
            for (Category category : categories) {
                CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());
                listCategories.add(dto);
            }
            return listCategories;
        } catch (BrandNotFoundException e) {
            throw new BrandNotFoundRestException();
        }
    }

}
