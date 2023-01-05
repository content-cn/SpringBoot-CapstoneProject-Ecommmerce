package com.springboot.ecommerceapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CheckoutItemDto {

    private String productName;
    private String quantity;
    private Double price;
    private Integer productId;
    private Integer userId;

}
