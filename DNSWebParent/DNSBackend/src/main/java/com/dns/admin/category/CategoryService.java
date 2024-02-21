package com.dns.admin.category;

import com.dns.admin.FileUploadedUtil;
import com.dns.admin.user.UserNotFoundException;
import com.dns.common.entity.Category;
import com.dns.common.entity.User;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author valeriali on {05.02.2024}
 * @project TechnoShopProject
 */
@Service
@Transactional
public class CategoryService {
    public static final int CATEGORIES_PER_PAGE = 4;
    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public List<Category> listAll(String sortDir) {
        Sort sort = Sort.by("name");

        if (sortDir.equals("asc")) {
            sort = sort.ascending();
        } else if (sort.equals("desc")) {
            sort = sort.descending();
        }

        List<Category> rootCategories = repo.findRootCategories(sort);
        return listHierarchicalCategories(rootCategories, sortDir);
    }

    private List<Category> listHierarchicalCategories(List<Category> rootCategories, String sortDir) {
        List<Category> hierarchicalCategories = new ArrayList<>();
        for (Category rootCategory : rootCategories) {
            hierarchicalCategories.add(Category.copyFull(rootCategory));
            listSubHierarchicalCategories(hierarchicalCategories, rootCategory, 0, sortDir);
        }
        return hierarchicalCategories;
    }

    private void listSubHierarchicalCategories(List<Category> hierarchicalCategories, Category parent, int subLevel,
                                               String sortDir) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = sortSubCategories(parent.getChildren(), sortDir);

        for (Category subCategory : children) {
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < newSubLevel; i++) {
                name.append("--");
            }
            name.append(subCategory.getName());
            hierarchicalCategories.add(Category.copyFull(subCategory, String.valueOf(name)));
            listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel, sortDir);
        }
    }




    public Page<Category> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, CATEGORIES_PER_PAGE, sort);

        if (keyword != null) {
            return repo.findAll(keyword, pageable);
        }
        return repo.findAll(pageable);
    }

    public Category save(Category category) {
        return repo.save(category);
    }


    public List<Category> listCategoriesUsedInForm() {
        List<Category> categoriesUsedInForm = new ArrayList<>();
        List<Category> categoriesInDB = repo.findRootCategories(Sort.by("name").ascending());

        for (Category category : categoriesInDB) {
            categoriesUsedInForm.add(Category.copyIdAndName(category));
            listSubCategoriesUsedInForm(categoriesUsedInForm, category, 0);
        }

        return  categoriesUsedInForm;
    }

    private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = sortSubCategories(parent.getChildren());

        for (Category subCategory : children) {
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < newSubLevel; i++) {
                name.append("--");
            }
            name.append(subCategory.getName());
            categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), String.valueOf(name)));
            listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
        }
    }


    public Category get(Integer id) throws CategoryNotFoundException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new CategoryNotFoundException("Категория с ID: " + id + " не найдена");
        }
    }

    public String checkUnique(Integer id, String name, String alias) {
        boolean isCreatingNew = (id==null || id == 0);
        Category categoryByName = repo.findByName(name);
        System.out.println("Problem");
        if (isCreatingNew) {
            if (categoryByName != null) {
                return "DuplicateName";
            } else {
                Category categoryByAlias = repo.findByAlias(alias);
                if (categoryByAlias != null) {
                    return "DuplicateAlias";
                }
            }
        } else {
            if (categoryByName != null && categoryByName.getId() != id) {
                return "DuplicateName";
            }
            Category categoryByAlias = repo.findByAlias(alias);
            if (categoryByAlias != null && categoryByAlias.getId() != id) {
                return "DuplicateAlias";
            }
        }
        return "OK";
    }

    private SortedSet<Category> sortSubCategories(Set<Category> children) {
        return sortSubCategories(children, "asc");
    }

    private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir) {
        SortedSet<Category> sortedChildren = new TreeSet<>((cat1, cat2) -> {
            if (sortDir.equals("asc")) {
                return cat1.getName().compareTo(cat2.getName());
            } else {
                return cat2.getName().compareTo(cat1.getName());
            }
        });
        sortedChildren.addAll(children);
        return sortedChildren;
    }

    public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
        repo.updateEnabledStatus(id, enabled);
    }

    public void delete(Integer id) throws CategoryNotFoundException {
        Long countById = repo.countById(id);
        if (countById == null || countById == 0) {
            throw new CategoryNotFoundException("Категория с ID: " + id + " не найдена");
        }
        repo.deleteById(id);
    }
}
