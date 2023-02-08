package com.springboot.ecommerceapp.dto;

import com.springboot.ecommerceapp.models.Product;
import lombok.Data;

@Data
public class ProductDto {

    private Integer id;

    private String name;

    private String image;

    private double maxPrice;

    private double price;

    private String category;

    public ProductDto(Product product, String category) {
        this.id = product.getId();
        this.image = product.getImage();
        this.maxPrice = product.getMaxPrice();
        this.price = product.getPrice();
        this.category = category;
        this.name = product.getName();
    }
}
