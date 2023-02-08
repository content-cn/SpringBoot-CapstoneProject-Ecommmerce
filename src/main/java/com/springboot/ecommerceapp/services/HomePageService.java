package com.springboot.ecommerceapp.services;

import com.springboot.ecommerceapp.dto.ProductDto;
import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HomePageService {

    private CategoryService categoryService;

    private ProductService productService;

    @Autowired
    public HomePageService(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    public Map<String, List<ProductDto>> getHomePageProducts() {
        List<Category> categories = categoryService.getAllCategory();

        Map<Integer, String> categoriesMap = categories.stream().collect(Collectors.toMap(Category::getId, Category::getName));

        List<Product> products = productService.getAllProducts();

        Map<String, List<ProductDto>> result = new HashMap<>();

        products.stream().filter(product -> product.getCategoryId() != null)
                .forEach(product -> {
                    String categoryName = categoriesMap.get(product.getCategoryId());

                    if (categoryName != null) {
                        result.putIfAbsent(categoryName, new ArrayList<>());
                        ProductDto productDto = new ProductDto(product, categoryName);
                        result.get(categoryName).add(productDto);
                    }
                });


        return result;
    }


    public Map<String, List<ProductDto>> searchProducts(String pattern) {
        List<Category> categories = categoryService.getAllCategory();

        Map<Integer, String> categoriesMap = categories.stream().collect(Collectors.toMap(Category::getId, Category::getName));

        List<Product> products = productService.getProductsByPattern(pattern);

        Map<String, List<ProductDto>> result = new HashMap<>();

        products.stream().filter(product -> product.getCategoryId() != null)
            .forEach(product -> {
                String categoryName = categoriesMap.get(product.getCategoryId());

                if (categoryName != null) {
                    result.putIfAbsent(categoryName, new ArrayList<>());
                    ProductDto productDto = new ProductDto(product, categoryName);
                    result.get(categoryName).add(productDto);
                }
            });


        return result;
    }

}
