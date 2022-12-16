package com.springboot.ecommerceapp.services;

import com.springboot.ecommerceapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepostiory;

}
