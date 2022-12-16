package com.springboot.ecommerceapp.exception;

public class AuthenticationFailedException extends IllegalArgumentException {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
