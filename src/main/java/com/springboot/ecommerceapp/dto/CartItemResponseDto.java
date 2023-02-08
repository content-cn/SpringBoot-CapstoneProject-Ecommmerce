package com.springboot.ecommerceapp.dto;

import com.springboot.ecommerceapp.models.CartItem;
import com.springboot.ecommerceapp.models.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class CartItemResponseDto {
    private Integer id;
    private Integer userId;
    private Integer quantity;
    private Integer productId;
    private double price;
    private ProductDto product;

    public CartItemResponseDto(CartItem cart){
        this.setId(cart.getId());
        this.setUserId(cart.getUserId());
        this.setQuantity(cart.getQuantity());
        this.setProductId(cart.getProductId());
    }

    public CartItem getCartFromDTO(CartItemResponseDto dto) {
        CartItem cart =  new CartItem();
        cart.setId(dto.getId());
        cart.setProductId(dto.getProductId());
        cart.setQuantity(dto.getQuantity());
        cart.setUserId(dto.getUserId());
        return cart;
    }
}
