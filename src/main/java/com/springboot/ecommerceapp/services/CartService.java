package com.springboot.ecommerceapp.services;

import com.springboot.ecommerceapp.dto.CartResponseDto;
import com.springboot.ecommerceapp.dto.CartItemResponseDto;
import com.springboot.ecommerceapp.dto.CartItemRequestDto;
import com.springboot.ecommerceapp.dto.ProductDto;
import com.springboot.ecommerceapp.exception.CartItemNotExistException;
import com.springboot.ecommerceapp.models.CartItem;
import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.models.User;
import com.springboot.ecommerceapp.repositories.CartItemRepository;
import com.springboot.ecommerceapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private CartItemRepository cartRepository;

    private ProductRepository productRepository;

    private CategoryService categoryService;

    @Autowired
    public CartService(CartItemRepository cartRepository, ProductRepository productRepository, CategoryService categoryService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public void addToCart(CartItemRequestDto requestDto) {
        CartItem cart = requestDto.convertToCartItem();
        cartRepository.save(cart);
    }

    public CartResponseDto getCart(Integer userId) {
        List<CartItem> cartList = cartRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
        List<CartItemResponseDto> cartItems = cartList.stream().map(c -> new CartItemResponseDto(c)).toList();
        double totalCost = 0;

        Map<Integer, String> map = categoryService.getAllCategory().stream().collect(Collectors.toMap(Category::getId, Category::getName));

        for(CartItemResponseDto dto: cartItems) {
            Optional<Product> productOptional = productRepository.findById(dto.getProductId());
            if (productOptional.isPresent()) {
                dto.setPrice(productOptional.get().getPrice());
                totalCost += (productOptional.get().getPrice()* dto.getQuantity());
                Product p = productOptional.get();
                dto.setProduct(new ProductDto(p, map.get(p.getCategoryId())));
            }

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

    public void deleteUserCartItems(Integer userId) {
        cartRepository.deleteByUserId(userId);
    }

}
