package com.springboot.ecommerceapp.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequestDTO {

    @NotNull(message = "Provide a valid username or email")
    private String usernameOrEmail;

    @NotNull(message = "Provide a valid password")
    private String password;
}
