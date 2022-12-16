package com.springboot.ecommerceapp.services;

import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HomePageService {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    public Map<String, List<Product>> getHomePageProducts() {
        List<Category> categories = categoryService.getAllCategory();

        Map<Integer, String> categoriesMap = categories.stream().collect(Collectors.toMap(Category::getId, Category::getName));

        List<Product> products = productService.getAllProducts();

        Map<String, List<Product>> result = products.stream().filter(product -> product.getCategoryId() != null)
                .collect(Collectors.groupingBy(p -> categoriesMap.get(p.getCategoryId())));

        return result;
    }
}
