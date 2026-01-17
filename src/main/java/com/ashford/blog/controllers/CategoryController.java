package com.ashford.blog.controllers;


import com.ashford.blog.domain.dtos.CategoryDto;
import com.ashford.blog.domain.dtos.CreateCategoryRequest;
import com.ashford.blog.domain.entities.Category;
import com.ashford.blog.mappers.CategoryMapper;
import com.ashford.blog.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories(){
    List<CategoryDto> categories=categoryService.listCategories()
            .stream()
            .map(categoryMapper::toDto)
            .toList();

    return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest){
        // Implementation for creating a category goes here
        Category categoryToCreate=categoryMapper.toEntity(createCategoryRequest);
        Category savedCategory=categoryService.createCategory(categoryToCreate);
        return new ResponseEntity<>(categoryMapper.toDto(savedCategory), HttpStatus.CREATED);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id){
        // Implementation for deleting a category goes here
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
