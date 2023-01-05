package com.springboot.ecommerceapp.repositories;

import com.springboot.ecommerceapp.models.Role;
import com.springboot.ecommerceapp.models.RoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMappingRepository extends JpaRepository<RoleMapping, Integer> {
    List<Role> findByUserId(Integer userId);
}
