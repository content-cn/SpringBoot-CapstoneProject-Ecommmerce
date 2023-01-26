package com.springboot.ecommerceapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductData {
    private String name;
    private String img;
    private String price;
    private String mrp;
    private String id;
}
