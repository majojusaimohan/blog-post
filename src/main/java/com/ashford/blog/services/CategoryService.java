package com.ashford.blog.services;

import com.ashford.blog.domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> listCategories();
    Category createCategory(Category category);
    void deleteCategoryById(UUID id);
}
