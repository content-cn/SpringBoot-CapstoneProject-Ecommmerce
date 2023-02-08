package com.springboot.ecommerceapp.controller;

import com.springboot.ecommerceapp.dto.LoginRequestDTO;
import com.springboot.ecommerceapp.dto.LoginResponseDTO;
import com.springboot.ecommerceapp.dto.SignUpDTO;
import com.springboot.ecommerceapp.models.Role;
import com.springboot.ecommerceapp.models.User;
import com.springboot.ecommerceapp.repositories.RoleRepository;
import com.springboot.ecommerceapp.repositories.UserRepository;
import com.springboot.ecommerceapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/test")
    public String hello() {
        return "hello";
    }

    @PostMapping("/signin")
    public LoginResponseDTO authenticateUser(@RequestBody @Valid LoginRequestDTO loginDto){
        return authService.authenticateUser(loginDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid SignUpDTO signUpDto){
        return authService.signUpuser(signUpDto, null);
    }
}
