package com.springboot.ecommerceapp.unittests;

import com.springboot.ecommerceapp.dto.CartItemRequestDto;
import com.springboot.ecommerceapp.dto.CartResponseDto;
import com.springboot.ecommerceapp.exception.CartItemNotExistException;
import com.springboot.ecommerceapp.exception.CategoryNotFoundException;
import com.springboot.ecommerceapp.models.CartItem;
import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.repositories.CartItemRepository;
import com.springboot.ecommerceapp.repositories.ProductRepository;
import com.springboot.ecommerceapp.services.CartService;
import com.springboot.ecommerceapp.services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CartServiceTests {

  @Mock
  private CartItemRepository cartRepository;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private CategoryService categoryService;

  @InjectMocks
  private CartService cartService;

  private CartItem cartItem;

  @Before
  public void setup() {
    cartItem = new CartItem();
    cartItem.setId(1);
    cartItem.setCreatedDate(new Date());
    cartItem.setUserId(1);
    cartItem.setQuantity(2);
    cartItem.setProductId(101);
  }

  @DisplayName("Add a item to the cart")
  @Test
  public void addToCart() {
    when(cartRepository.save(any(CartItem.class))).thenAnswer(invocation -> {
      return cartItem;
    });

    CartItemRequestDto cartItemRequestDto = new CartItemRequestDto();
    cartItemRequestDto.setUserId(cartItem.getUserId());
    cartItemRequestDto.setId(cartItem.getId());
    cartItemRequestDto.setProductId(cartItem.getProductId());
    cartItemRequestDto.setQuantity(cartItem.getQuantity());

    CartItem savedCartItem = cartService.addToCart(cartItemRequestDto);

    assertThat(savedCartItem).isNotNull();
    assertThat(savedCartItem.getProductId()).isEqualTo(this.cartItem.getProductId());
  }

  @DisplayName("Update an item in the cart")
  @Test
  public void updateCart() {
    when(cartRepository.save(any(CartItem.class))).thenAnswer(invocation -> {
      return cartItem;
    });

    CartItemRequestDto cartItemRequestDto = new CartItemRequestDto();
    cartItemRequestDto.setUserId(cartItem.getUserId());
    cartItemRequestDto.setId(cartItem.getId());
    cartItemRequestDto.setProductId(cartItem.getProductId());
    cartItemRequestDto.setQuantity(cartItem.getQuantity());

    Product product = new Product();
    product.setId(cartItem.getId());

    CartItem savedCartItem = cartService.updateCartItem(cartItemRequestDto, cartItem.getUserId(), product);

    assertThat(savedCartItem).isNotNull();
    assertThat(savedCartItem.getProductId()).isEqualTo(this.cartItem.getProductId());
  }

  @DisplayName("Delete cart items for a user")
  @Test
  public void deleteUserCartItems() throws CategoryNotFoundException {
    when(cartRepository.deleteByUserId(any(Integer.class))).thenAnswer(invocation -> {
      return Arrays.asList(cartItem);
    });


    List<CartItem> deleteUserCartItems = cartService.deleteUserCartItems(cartItem.getUserId());
    CartItem deletedCartItem = deleteUserCartItems.get(0);

    assertThat(deletedCartItem).isNotNull();
    assertThat(deletedCartItem.getId()).isEqualTo(cartItem.getId());
  }

  @DisplayName("Delete cart items by Id")
  @Test
  public void deleteCartItem() throws CartItemNotExistException {

    when(cartRepository.existsById(any(Integer.class))).thenAnswer(invocation -> {
      return true;
    });

    cartService.deleteCartItem(cartItem.getId(), cartItem.getUserId());
  }

  @DisplayName("Get cart items")
  @Test
  public void getCartItems() throws CartItemNotExistException {
    when(cartRepository.findAllByUserIdOrderByCreatedDateDesc(any(Integer.class))).thenAnswer(invocation -> {
      return Arrays.asList(cartItem);
    });

    when(categoryService.getAllCategory()).thenAnswer(invocation -> {
      Category category = new Category();
      category.setName("Electronics");
      category.setId(1);
      return Arrays.asList(category);
    });

    when(productRepository.findById(any(Integer.class))).thenAnswer(invocation -> {
      Product product = new Product();
      product.setId(101);
      product.setName("Sony OLED TV");
      product.setCategoryId(1);
      product.setPrice(300);
      return Optional.of(product);
    });


    CartResponseDto cartResponseDto = cartService.getCart(cartItem.getUserId());


    assertThat(cartResponseDto).isNotNull();
    assertThat(cartResponseDto.getCartItems().size()).isEqualTo(1);
  }


}
