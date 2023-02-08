package com.springboot.ecommerceapp.services;

import com.springboot.ecommerceapp.exception.CategoryNotFoundException;
import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public Category addOrUpdateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategory() {
        List<Category> result = new ArrayList<>();
        categoryRepository.findAll().forEach(result::add);
        return result;
    }

    public void removeCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    public Category getCategory(Integer id) throws CategoryNotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        }

        throw new CategoryNotFoundException("Category not found for id: " + id);
    }

}
