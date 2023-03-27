package com.springboot.ecommerceapp.unittests;

import com.springboot.ecommerceapp.dto.LoginRequestDTO;
import com.springboot.ecommerceapp.dto.LoginResponseDTO;
import com.springboot.ecommerceapp.dto.SignUpDTO;
import com.springboot.ecommerceapp.exception.CategoryNotFoundException;
import com.springboot.ecommerceapp.exception.UserNotFoundException;
import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.models.Role;
import com.springboot.ecommerceapp.models.User;
import com.springboot.ecommerceapp.repositories.RoleRepository;
import com.springboot.ecommerceapp.repositories.UserRepository;
import com.springboot.ecommerceapp.services.AuthService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AuthServiceTests {

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private UserRepository userRepository;

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AuthService authService;

  private User user;

  private Role role;

  @Before
  public void setup() {

    user = new User();
    user.setUsername("ajay101");
    user.setEmail("ajay@gmail.com");
    user.setName("Ajay");
    user.setPassword("password");
    user.setId(1);

    role = new Role();
    role.setId(1);
    role.setName("Admin");

  }

  @DisplayName("Login a user")
  @Test
  public void loginUser() {
    when(authenticationManager.authenticate(any(Authentication.class))).thenAnswer(invocation -> {
      return new UsernamePasswordAuthenticationToken(user.getUsername(), null);
    });

    when(userRepository.findByEmail(any(String.class))).thenAnswer(invocation -> {
      return Optional.of(user);
    });

    LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
    loginRequestDTO.setUsernameOrEmail(user.getEmail());
    loginRequestDTO.setPassword(user.getPassword());

    LoginResponseDTO loginResponseDTO = authService.authenticateUser(loginRequestDTO);

    assertThat(loginResponseDTO).isNotNull();
    assertThat(loginResponseDTO.getName()).isEqualTo(this.user.getName());
  }

  @DisplayName("Signup a user")
  @Test
  public void signupUser() throws CategoryNotFoundException {

    when(userRepository.existsByUsername(user.getUsername())).thenAnswer(invocation -> {
      return false;
    });

    when(userRepository.existsByEmail(user.getEmail())).thenAnswer(invocation -> {
      return false;
    });

    when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
      return user;
    });

    when(roleRepository.findByName(any(String.class))).thenAnswer(invocation -> {
      return Optional.of(role);
    });

    when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> {
      return role;
    });

    SignUpDTO signUpDTO = new SignUpDTO();
    signUpDTO.setEmail(user.getEmail());
    signUpDTO.setUsername(user.getUsername());
    signUpDTO.setName(user.getName());
    signUpDTO.setPassword(user.getPassword());

    ResponseEntity entity = authService.signUpuser(signUpDTO, "admin");

    assertThat(entity).isNotNull();
    assertThat(entity.getStatusCode().value()).isEqualTo(200);
  }

}
