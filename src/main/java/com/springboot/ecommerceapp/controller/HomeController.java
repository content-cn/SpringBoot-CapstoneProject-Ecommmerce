package com.springboot.ecommerceapp.controller;

import com.netflix.discovery.converters.Auto;
import com.springboot.ecommerceapp.dto.ProductDto;
import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.repositories.ProductRepository;
import com.springboot.ecommerceapp.services.HomePageService;
import com.springboot.ecommerceapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/home")
public class HomeController {

    HomePageService homePageService;

    @Autowired
    public HomeController(HomePageService homePageService) {
        this.homePageService = homePageService;
    }

    @GetMapping()
    public Map<String, List<ProductDto>> getHomePage() {
        return homePageService.getHomePageProducts();
    }
}