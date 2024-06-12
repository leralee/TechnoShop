package com.dns.admin.brand;

import com.dns.common.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BrandService {
    public static final int BRANDS_PER_PAGE = 4;
    private final BrandRepository repo;

    public BrandService(BrandRepository repo) {
        this.repo = repo;
    }

    public List<Brand> listAll() {
        return repo.findAll();
    }

    public Page<Brand> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, BRANDS_PER_PAGE, sort);

        if (keyword != null) {
            return repo.findAll(keyword, pageable);
        }
        return repo.findAll(pageable);
    }

    public Brand save(Brand brand) {
        return repo.save(brand);
    }


    public Brand get(Integer id) throws BrandNotFoundException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new BrandNotFoundException("Бренд с ID: " + id + " не найден");
        }
    }

    public String checkUnique(Integer id, String name) {
        boolean isCreatingNew = (id==null || id == 0);
        Brand brandByName = repo.findByName(name);
        if (isCreatingNew) {
            if (brandByName != null) {
                return "Duplicated";
            }
        } else {
            if (brandByName != null && !Objects.equals(brandByName.getId(), id)) {
                return "Duplicated";
            }
        }
        return "OK";
    }

    public void delete(Integer id) throws BrandNotFoundException {
        Long countById = repo.countById(id);
        if (countById == null || countById == 0) {
            throw new BrandNotFoundException("Бренд с ID: " + id + " не найден");
        }
        repo.deleteById(id);
    }
}
