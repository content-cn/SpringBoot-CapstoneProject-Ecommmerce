package com.springboot.ecommerceapp.models;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@ToString
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Provide a valid product name")
    private String name;

    @NotBlank(message = "Provide a valid product image")
    private String image;

    @NotNull(message = "Provide a valid product price")
    private double price;

    private double maxPrice;

    private double weight;

    @NotNull(message = "Provide a valid categoryID")
    private Integer categoryId;
}
