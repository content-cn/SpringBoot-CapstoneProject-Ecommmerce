package com.springboot.ecommerceapp.unittests;

import com.springboot.ecommerceapp.exception.CategoryNotFoundException;
import com.springboot.ecommerceapp.exception.UserNotFoundException;
import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.models.OrderItem;
import com.springboot.ecommerceapp.repositories.OrderItemRepository;
import com.springboot.ecommerceapp.services.OrderItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderItemServiceTests {

  @Mock
  private OrderItemRepository orderItemRepository;

  @InjectMocks
  private OrderItemService orderItemService;

  private OrderItem orderItem;

  @Before
  public void setup() {
    orderItem = new OrderItem();
    orderItem.setId(1);
    orderItem.setPrice(300d);
    orderItem.setQuantity(1);
  }

  @DisplayName("Add an order item")
  @Test
  public void addOrderItem() {
    given(orderItemRepository.findById(orderItem.getId()))
        .willReturn(Optional.empty());

    when(orderItemRepository.save(any(OrderItem.class))).thenAnswer(invocation -> {
      return orderItem;
    });

    OrderItem savedOrderItem = orderItemService.addOrderedProducts(this.orderItem);

    assertThat(savedOrderItem).isNotNull();
    assertThat(savedOrderItem.getOrderId()).isEqualTo(this.orderItem.getOrderId());
  }
}
