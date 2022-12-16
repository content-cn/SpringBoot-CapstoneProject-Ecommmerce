package com.springboot.ecommerceapp.services;

import com.springboot.ecommerceapp.dto.CartResponseDto;
import com.springboot.ecommerceapp.dto.CartItemResponseDto;
import com.springboot.ecommerceapp.dto.CartItemRequestDto;
import com.springboot.ecommerceapp.exception.CartItemNotExistException;
import com.springboot.ecommerceapp.models.CartItem;
import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartRepository;

    public void addToCart(CartItemRequestDto requestDto) {
        CartItem cart = requestDto.convertToCartItem();
        cartRepository.save(cart);
    }

    public CartResponseDto getCart(Integer userId) {
        List<CartItem> cartList = cartRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
        List<CartItemResponseDto> cartItems = cartList.stream().map(c -> new CartItemResponseDto(c)).toList();
        double totalCost = 0;
        for(CartItemResponseDto dto: cartItems) {
            totalCost += (dto.getProduct().getPrice()* dto.getQuantity());
        }

        return new CartResponseDto(cartItems, totalCost);
    }

    public void updateCartItem(CartItemRequestDto dto, int userId, Product product) {
        CartItem cart = dto.convertToCartItem();
        cart.setQuantity(dto.getQuantity());
        cart.setUserId(userId);
        cart.setId(dto.getId());
        cart.setProductId(product.getId());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);
    }

    public void deleteCartItem(int id, int userId) throws CartItemNotExistException {
        if (!cartRepository.existsById(id)) {
            throw new CartItemNotExistException("Cart Id is invalid: " +  id);
        }

        cartRepository.deleteById(id);
    }

}
