package com.springboot.ecommerceapp.services;

import com.springboot.ecommerceapp.models.OrderItem;
import com.springboot.ecommerceapp.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {


    private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem addOrderedProducts(OrderItem orderItem) {
        return  orderItemRepository.save(orderItem);
    }
}
