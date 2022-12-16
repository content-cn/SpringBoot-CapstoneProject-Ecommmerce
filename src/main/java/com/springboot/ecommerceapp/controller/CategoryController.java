package com.springboot.ecommerceapp.controller;

import com.springboot.ecommerceapp.exception.CategoryNotFoundException;
import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.repositories.CategoryRepository;
import com.springboot.ecommerceapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public List<Category> getCategories() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable(value = "id") Integer id) throws CategoryNotFoundException {
        return categoryService.getCategory(id);
    }

    @PostMapping
    public Category saveCategory(@Validated @RequestBody Category category) {
        return categoryService.addOrUpdateCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable(value = "id") Integer id) {
        categoryService.removeCategory(id);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable(value = "id") Integer id, @Validated @RequestBody Category category) {
        category.setId(id);
        return categoryService.addOrUpdateCategory(category);
    }
}