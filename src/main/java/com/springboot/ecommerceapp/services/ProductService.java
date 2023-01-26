package com.springboot.ecommerceapp.services;

import com.springboot.ecommerceapp.dto.ProductDto;
import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }


    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        productRepository.findAll().forEach(list::add);
        return list;
    }

    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Optional<Product> getProduct(Integer id) {
        return productRepository.findById(id);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public ResponseEntity<Product> updateProduct(Integer id, Product product) {
        if (productRepository.existsById(id)) {
            Product entity = productRepository.save(product);
            return ResponseEntity.ok().body(entity);
        }

        return ResponseEntity.notFound().build();
    }

    public List<ProductDto> getProductByCategoryId(Integer categoryId) {
        List<Product> products = productRepository.findAllByCategoryId(categoryId);

        List<Category> categories = categoryService.getAllCategory();
        Map<Integer, String> map = categories.stream().collect(Collectors.toMap(Category::getId, Category::getName));

        return products.stream().map(product -> new ProductDto(product, map.get(categoryId))).toList();
    }

    public List<ProductDto> getProductByCategoryName(String categoryName) {
        List<Category> categories = categoryService.getAllCategory();
        categories.stream().forEach(category -> category.setName(category.getName().toLowerCase()));
        Map<String, Integer> map = categories.stream().collect(Collectors.toMap(Category::getName, Category::getId));

        return getProductByCategoryId(map.get(categoryName.toLowerCase()));
    }

}
