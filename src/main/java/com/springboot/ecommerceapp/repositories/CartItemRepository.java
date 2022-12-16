package com.springboot.ecommerceapp.repositories;

import com.springboot.ecommerceapp.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findAllByUserIdOrderByCreatedDateDesc(Integer userId);
}
