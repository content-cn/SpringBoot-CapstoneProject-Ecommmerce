package com.springboot.ecommerceapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderRequestDto {
  private Integer userId;

  private Integer addressId;

  private String payment;
}
