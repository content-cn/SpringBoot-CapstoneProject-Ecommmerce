package com.springboot.ecommerceapp.controller;

import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping()
    public List<Product> getProducts() {
        List<Product> result = new ArrayList<>();
        productRepository.findAll().forEach(p -> result.add(p));
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok().body(productOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Product saveProduct(@Validated @RequestBody Product product) {

        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable(value = "id") Integer id) {
        productRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Integer id, @Validated @RequestBody Product product) {
        if (productRepository.existsById(id)) {
            Product entity = productRepository.save(product);
            return ResponseEntity.ok().body(entity);
        }

        return ResponseEntity.notFound().build();
    }
}