package com.springboot.ecommerceapp.repositories;

import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
