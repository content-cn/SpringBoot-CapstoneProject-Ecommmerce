package com.springboot.ecommerceapp.controller;

import com.springboot.ecommerceapp.dto.OrderItemResponseDto;
import com.springboot.ecommerceapp.dto.OrderRequestDto;
import com.springboot.ecommerceapp.dto.OrderResponseDto;
import com.springboot.ecommerceapp.exception.OrderNotFoundException;
import com.springboot.ecommerceapp.exception.UserNotFoundException;
import com.springboot.ecommerceapp.models.Order;
import com.springboot.ecommerceapp.models.User;
import com.springboot.ecommerceapp.services.OrderService;
import com.springboot.ecommerceapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    private UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService =  userService;
    }

    @PostMapping("/add")
    public ResponseEntity placeOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) throws UserNotFoundException {
        orderService.placeOrder(orderRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public List<OrderResponseDto> getAllOrders(@RequestHeader("userId") Integer userId) throws UserNotFoundException, OrderNotFoundException {
        return orderService.listOrders(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable("id") Integer id) {
        try {
            OrderResponseDto order = orderService.getOrder(id);
            return ResponseEntity.ok(order);
        } catch (OrderNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
