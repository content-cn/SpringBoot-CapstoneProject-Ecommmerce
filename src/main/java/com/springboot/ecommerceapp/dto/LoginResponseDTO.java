package com.springboot.ecommerceapp.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String username;
    private String email;
    private String token;
    private String name;
    private Integer id;
}
