package com.dns.admin.brand;

import com.dns.admin.FileUploadedUtil;
import com.dns.admin.brand.exporter.BrandCsvExporter;
import com.dns.admin.category.CategoryService;
import com.dns.admin.category.exporter.CategoryCsvExporter;
import com.dns.admin.user.UserService;
import com.dns.common.entity.Brand;
import com.dns.common.entity.Category;
import com.dns.common.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;


@Controller
@Transactional
public class BrandController {

    final BrandService brandService;
    final CategoryService categoryService;

    @Autowired
    public BrandController(BrandService service, CategoryService categoryService) {
        this.brandService = service;
        this.categoryService = categoryService;
    }

    @GetMapping("/brands")
    public String listFirstPage(Model model) {
        return listByPage(1, "name", "asc", null, model);
    }

    @GetMapping("/brands/page/{pageNum}")
    public String listByPage(@PathVariable("pageNum") int pageNum,
                             @RequestParam(value = "sortField", required = false) String sortField,
                             @RequestParam(value = "sortDir") String sortDir,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             Model model) {

        Page<Brand> page = brandService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Brand> listBrands = page.getContent();

        long startCount = (long) (pageNum - 1) * BrandService.BRANDS_PER_PAGE + 1;
        long endCount = startCount + BrandService.BRANDS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("listBrands", listBrands);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);

        return "brands/brands";
    }


    @GetMapping("/brands/new")
    public String newCategory(Model model) {
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();
        model.addAttribute("brand", new Brand());
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("pageTitle", "Создание нового бренда");
        return "brands/brand_form";
    }

    @PostMapping("/brands/save")
    public String saveCategory(Brand brand, @RequestParam("fileImage") MultipartFile multipartFile,
                               RedirectAttributes redirectAttributes) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            brand.setLogo(fileName);
            Brand savedBrand = brandService.save(brand);
            String uploadDir = "../brand-logos/" + savedBrand.getId();
            FileUploadedUtil.cleanDir(uploadDir);
            FileUploadedUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            brandService.save(brand);
        }
        redirectAttributes.addFlashAttribute("message", "Бренд успешно сохранен");
        return "redirect:/brands";
    }

    @GetMapping("brands/edit/{id}")
    public String editCategory(@PathVariable(name = "id") Integer id, Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            Brand brand = brandService.get(id);
            List<Category> listCategories = categoryService.listCategoriesUsedInForm();
            model.addAttribute("brand", brand);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Редактирование бренда (ID: " + id + ")");

            return "brands/brand_form";
        } catch (BrandNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/brands";
        }
    }

    @GetMapping("brands/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             RedirectAttributes redirectAttributes) {
        try {
            brandService.delete(id);
            String categoryDir = "../brand-logos/" + id;
            FileUploadedUtil.removeDir(categoryDir);
            redirectAttributes.addFlashAttribute("message",
                    "Бренд с ID: " + id + " удален");
        } catch (BrandNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/brands";

    }


    @GetMapping("/brands/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<Brand> listBrands = brandService.listAll();
        BrandCsvExporter exporter = new BrandCsvExporter();
        exporter.export(listBrands, response);
    }
}
