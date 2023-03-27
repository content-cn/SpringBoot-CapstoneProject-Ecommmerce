package com.springboot.ecommerceapp.services;

import com.springboot.ecommerceapp.dto.LoginRequestDTO;
import com.springboot.ecommerceapp.dto.LoginResponseDTO;
import com.springboot.ecommerceapp.dto.SignUpDTO;
import com.springboot.ecommerceapp.models.Role;
import com.springboot.ecommerceapp.models.RoleMapping;
import com.springboot.ecommerceapp.models.User;
import com.springboot.ecommerceapp.repositories.RoleRepository;
import com.springboot.ecommerceapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public LoginResponseDTO authenticateUser(LoginRequestDTO requestDTO) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                requestDTO.getUsernameOrEmail(), requestDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setEmail(authentication.getName());

        Optional<User> userOptional = userRepository.findByEmail(authentication.getName());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            responseDTO.setName(user.getName());
            responseDTO.setEmail(user.getEmail());
            responseDTO.setUsername(user.getUsername());

            String usernamePassword = user.getUsername() + ":" + requestDTO.getPassword();
            String bytesEncoded = Base64.getEncoder().encodeToString(usernamePassword.getBytes());
            String token = "Basic " + bytesEncoded.toString();
            responseDTO.setToken(token);
            responseDTO.setId(user.getId());
        }

        return responseDTO;
    }

    public ResponseEntity signUpuser(SignUpDTO signUpDto, String role) {
        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (null == role || "".equals(role)) {
            role = "ROLE_USER";
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        user = userRepository.save(user);

        Optional<Role> optionalRole = roleRepository.findByName(role);
        if (optionalRole.isPresent()) {
            Role roles = optionalRole.get();
            RoleMapping roleMapping = new RoleMapping();
            roleMapping.setUserId(user.getId());
            roleMapping.setRoleId(roles.getId());
            roleRepository.save(roles);
        }

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

}
