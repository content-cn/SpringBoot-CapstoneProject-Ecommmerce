package com.springboot.ecommerceapp.exception;

public class CartItemNotExistException extends Exception {
    public CartItemNotExistException(String message) {
        super(message);
    }
}
