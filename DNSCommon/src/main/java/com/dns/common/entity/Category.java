package com.dns.common.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @Column(length = 64, nullable = false, unique = true)
    private String alias;

    @Column(length = 128, nullable = false)
    private String image;

    private boolean enabled;

    @Column(name = "all_parent_ids", length = 256, nullable = true)
    private String allParentIDs;

    @ManyToOne()
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new HashSet<>();

    public Category() {

    }

    public Category(Integer id) {
        this.id = id;
    }

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }

    public Category(String name) {
        this.name = name;
        this.alias = name;
        this.image = "default.png";
    }

    public Category(Integer id, String name, String alias) {
        this.id = id;
        this.name = name;
        this.alias = alias;
    }

    public static Category copyIdAndName(Category category) {
        Category copyCategory = new Category();
        copyCategory.setId(category.getId());
        copyCategory.setName(category.getName());
        return copyCategory;
    }

    public static Category copyIdAndName(Integer id, String name) {
        Category copyCategory = new Category();
        copyCategory.setId(id);
        copyCategory.setName(name);
        return copyCategory;
    }

    public static Category copyFull(Category category) {
        Category copyCategory = new Category();
        copyCategory.setId(category.getId());
        copyCategory.setName(category.getName());
        copyCategory.setImage(category.getImage());
        copyCategory.setAlias(category.getAlias());
        copyCategory.setEnabled(category.isEnabled());
        copyCategory.setHasChildren(!category.getChildren().isEmpty());
        return copyCategory;
    }
    public static Category copyFull(Category category, String name) {
        Category copyCategory = Category.copyFull(category);
        copyCategory.setName(name);
        return copyCategory;
    }


    @Transient
    public String getImagePath() {
        if (this.id == null) return "/images/image-thumbnail.png";
        return "/category-images/" + this.id + "/" + this.image;
    }

    @Transient
    private boolean hasChildren;

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

