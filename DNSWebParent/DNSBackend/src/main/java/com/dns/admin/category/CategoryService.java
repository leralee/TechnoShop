package com.dns.admin.category;

import com.dns.common.entity.Category;
import com.dns.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

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

    public List<Category> listAll() {
//        return repo.findAll(Sort.by("name").ascending());
        List<Category> rootCategories = repo.findRootCategories();
        return listHierarchicalCategories(rootCategories);
    }

    private List<Category> listHierarchicalCategories(List<Category> rootCategories) {
        List<Category> hierarchicalCategories = new ArrayList<>();
        for (Category rootCategory : rootCategories) {
            hierarchicalCategories.add(Category.copyFull(rootCategory));
            listSubHierarchicalCategories(hierarchicalCategories, rootCategory, 0);
        }
        return hierarchicalCategories;
    }

    private void listSubHierarchicalCategories(List<Category> hierarchicalCategories, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < newSubLevel; i++) {
                name.append("--");
            }
            name.append(subCategory.getName());
            hierarchicalCategories.add(Category.copyFull(subCategory, String.valueOf(name)));
            listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);
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
        List<Category> categoriesInDB = repo.findRootCategories();

        for (Category category : categoriesInDB) {
            categoriesUsedInForm.add(Category.copyIdAndName(category));
            listSubCategoriesUsedInForm(categoriesUsedInForm, category, 0);
        }

        return  categoriesUsedInForm;
    }

    private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

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

        if (isCreatingNew) {
            if (categoryByName != null) {
                return "DuplicateName";
            } else {
                Category categoryByAlias = repo.findByAlias(alias);
                if (categoryByAlias != null) {
                    return "DuplicateAlias";
                }
            }
        }
        return "OK";
    }
}
