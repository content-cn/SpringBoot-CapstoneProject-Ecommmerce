package com.springboot.ecommerceapp.repositories;

import com.springboot.ecommerceapp.models.Order;
import com.springboot.ecommerceapp.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAllByCategoryId(Integer categoryId);
    Optional<Product> findByName(String name);
}
