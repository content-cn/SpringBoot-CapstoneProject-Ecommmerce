package com.springboot.ecommerceapp.dto;

import com.springboot.ecommerceapp.models.Address;
import com.springboot.ecommerceapp.models.Order;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponseDto {
    private Integer id;

    private Date createdDate;

    private Double totalPrice;

    private Address address;

    private String payment;

    List<OrderItemResponseDto> orderItems = new ArrayList<>();

    public static OrderResponseDto convertToOrderResponse(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setCreatedDate(order.getCreatedDate());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setId(order.getId());
        dto.setPayment(order.getPaymentMethod());
        return dto;
    }
}
