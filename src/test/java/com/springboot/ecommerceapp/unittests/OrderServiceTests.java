package com.springboot.ecommerceapp.unittests;

import com.netflix.discovery.EurekaClient;
import com.springboot.ecommerceapp.dto.CartItemResponseDto;
import com.springboot.ecommerceapp.dto.CartResponseDto;
import com.springboot.ecommerceapp.dto.OrderRequestDto;
import com.springboot.ecommerceapp.exception.CategoryNotFoundException;
import com.springboot.ecommerceapp.exception.UserNotFoundException;
import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.models.Order;
import com.springboot.ecommerceapp.models.OrderItem;
import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.repositories.CategoryRepository;
import com.springboot.ecommerceapp.repositories.OrderItemRepository;
import com.springboot.ecommerceapp.repositories.OrderRepository;
import com.springboot.ecommerceapp.repositories.ProductRepository;
import com.springboot.ecommerceapp.services.CartService;
import com.springboot.ecommerceapp.services.CategoryService;
import com.springboot.ecommerceapp.services.OrderService;
import com.springboot.ecommerceapp.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderServiceTests {

  @Mock
  private CartService cartService;

  @Mock
  private OrderItemRepository orderItemRepository;

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private UserService userService;

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private OrderService orderService;

  private Order order;

  private OrderItem orderItem;

  private Product product;

  @Mock
  private EurekaClient discoveryClient;

  @Before
  public void setup() {
    orderItem = new OrderItem();
    orderItem.setId(1);
    orderItem.setPrice(300d);
    orderItem.setQuantity(1);
    orderItem.setOrderId(2);

    order = new Order();
    order.setPaymentMethod("COD");
    order.setId(2);
    order.setAddressId(5);
    order.setTotalPrice(300d);

    product = new Product();
    product.setId(1);
    product.setName("Sony OLED TV");
    product.setCategoryId(1);
    product.setPrice(300);
  }

  @DisplayName("Place an order")
  @Test
  public void validatePlaceOrder() throws UserNotFoundException {
    when(cartService.getCart(any(Integer.class))).thenAnswer(invocationOnMock -> {
      CartResponseDto cartResponseDto = new CartResponseDto();
      CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
      cartItemResponseDto.setId(1);
      cartItemResponseDto.setPrice(300d);
      cartItemResponseDto.setQuantity(1);

      cartResponseDto.setCartItems(Arrays.asList(cartItemResponseDto));
      cartResponseDto.setTotalCost(300d);

      return cartResponseDto;
    });

    when(orderRepository.save(any(Order.class))).thenAnswer(invocationOnMock -> {
      return order;
    });

    when(productRepository.findById(any(Integer.class))).thenAnswer(invocation -> {
      return Optional.of(product);
    });

    when(orderItemRepository.save(any(OrderItem.class))).thenAnswer(invocation -> {
      return orderItem;
    });

    when(cartService.deleteUserCartItems(any(Integer.class))).thenAnswer(invocation -> {
      return Arrays.asList();
    });

    when(userService.getUser(any(Integer.class))).thenAnswer(invocation -> {
      return null;
    });

    OrderRequestDto orderRequestDto = new OrderRequestDto();
    orderRequestDto.setUserId(1);
    orderRequestDto.setAddressId(1);
    orderRequestDto.setPayment("COD");

    Order savedOrder = orderService.placeOrder(orderRequestDto);

    assertThat(savedOrder).isNotNull();
  }

}
