package com.springboot.ecommerceapp.services;

import com.springboot.ecommerceapp.models.OrderItem;
import com.springboot.ecommerceapp.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public void addOrderedProducts(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
