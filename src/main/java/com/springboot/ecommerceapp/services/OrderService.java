package com.springboot.ecommerceapp.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.springboot.ecommerceapp.dto.CartItemResponseDto;
import com.springboot.ecommerceapp.dto.CartResponseDto;
import com.springboot.ecommerceapp.dto.OrderItemResponseDto;
import com.springboot.ecommerceapp.dto.OrderResponseDto;
import com.springboot.ecommerceapp.exception.OrderNotFoundException;
import com.springboot.ecommerceapp.exception.UserNotFoundException;
import com.springboot.ecommerceapp.models.*;
import com.springboot.ecommerceapp.repositories.OrderItemRepository;
import com.springboot.ecommerceapp.repositories.OrderRepository;
import com.springboot.ecommerceapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    CartService cartService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private EurekaClient discoveryClient;



    @Transactional
    public void placeOrder(Integer userId) throws UserNotFoundException {
        CartResponseDto cartResponseDto = cartService.getCart(userId);

        List<CartItemResponseDto> cartItemResponseDtoList = cartResponseDto.getCartItems();

        // create the order and save it
        Order order = new Order();
        order.setCreatedDate(new Date());
        order.setUserId(userId);
        order.setTotalPrice(cartResponseDto.getTotalCost());
        final Order savedOrder = orderRepository.save(order);

        cartItemResponseDtoList.stream().forEach(cartItem -> {
            OrderItem item = new OrderItem();
            item.setCreatedDate(new Date());
            Optional<Product> productOptional = productRepository.findById(cartItem.getProductId());
            if (productOptional.isPresent()) {
                item.setPrice(productOptional.get().getPrice());
            }
            item.setProductId(cartItem.getProductId());
            item.setQuantity(cartItem.getQuantity());
            item.setOrderId(savedOrder.getId());

            orderItemRepository.save(item);
        });

        cartService.deleteUserCartItems(userId);

        User user = userService.getUser(userId);

        String subject = "Order placed with order ID: " + order.getId();

        sendEmail(user.getEmail(), subject);

    }

    private void sendEmail(String email, String subject) {
        List<InstanceInfo> instances = discoveryClient.getApplication("NOTIFICATION_SERVICE").getInstances();
        if (instances != null && !instances.isEmpty()) {
            InstanceInfo instance = instances.get(0);
            String url = instance.getIPAddr();

            String finalUri = instance.getHomePageUrl() + "/email/sendEmail";

            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setSubject(subject);
            emailDetails.setMsgBody("Order placed successfully");
            emailDetails.setRecipient(email);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(finalUri, emailDetails, String.class);
        }
    }

    public List<Order> listOrders(Integer userId) {
        return orderRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
    }

    public OrderResponseDto getOrder(Integer orderId) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isPresent()) {
            OrderResponseDto orderResponseDto = OrderResponseDto.convertToOrderResponse(order.get());

            List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
            orderItems.stream().forEach(item -> {
                orderResponseDto.getOrderItems().add(OrderItemResponseDto.ConvertToOrderItemResponseDto(item));
            });

            return orderResponseDto;
        }

        throw new OrderNotFoundException("Order not found: " + orderId);
    }


}
