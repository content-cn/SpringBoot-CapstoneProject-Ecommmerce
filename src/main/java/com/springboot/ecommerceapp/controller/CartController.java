package com.springboot.ecommerceapp.controller;

import com.springboot.ecommerceapp.dto.CartResponseDto;
import com.springboot.ecommerceapp.dto.CartItemResponseDto;
import com.springboot.ecommerceapp.dto.CartItemRequestDto;
import com.springboot.ecommerceapp.exception.CartItemNotExistException;
import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.repositories.ProductRepository;
import com.springboot.ecommerceapp.services.CartService;
import com.springboot.ecommerceapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;
    private ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity addToCart(@RequestBody CartItemRequestDto requestDto, @RequestHeader("userId") Integer userId) {
        requestDto.setUserId(userId);
        cartService.addToCart(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public ResponseEntity<CartResponseDto> getCart(@RequestHeader("userId") Integer userId) {
        CartResponseDto cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity updateCartItem(@RequestBody @Valid CartItemRequestDto dto) {
        Optional<Product> optionalProduct = productService.getProduct(dto.getId());
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        cartService.updateCartItem(dto, dto.getUserId(), optionalProduct.get());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity deleteCartItem(@PathVariable("cartItemId") int itemId, @RequestHeader("userId") String userId) throws CartItemNotExistException {
        Integer uid = Integer.parseInt(userId);
        cartService.deleteCartItem(itemId, uid);
        return ResponseEntity.ok().build();
    }


}
