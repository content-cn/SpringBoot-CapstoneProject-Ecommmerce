package com.springboot.ecommerceapp.controller;

import com.springboot.ecommerceapp.dto.ProductDto;
import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.repositories.ProductRepository;
import com.springboot.ecommerceapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping()
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Integer id) {
        Optional<Product> productOptional = productService.getProduct(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok().body(productOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/category/{id}")
    public List<ProductDto> getProductsByCategory(@PathVariable(value = "id") Integer id) {
        return productService.getProductByCategoryId(id);
    }

    @GetMapping("/categoryname")
    public List<ProductDto> getProductsByCategory(@RequestParam(value = "name") String name) {
        return productService.getProductByCategoryName(name);
    }

    @PostMapping
    public Product saveProduct(@Validated @RequestBody Product product) {
        return productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable(value = "id") Integer id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Integer id, @Valid @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
}