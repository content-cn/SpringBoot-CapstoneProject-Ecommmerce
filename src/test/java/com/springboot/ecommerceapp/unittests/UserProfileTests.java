package com.springboot.ecommerceapp.unittests;

import com.springboot.ecommerceapp.exception.UserNotFoundException;
import com.springboot.ecommerceapp.models.User;
import com.springboot.ecommerceapp.repositories.UserRepository;
import com.springboot.ecommerceapp.services.UserService;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserProfileTests {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private User user;

  @Before
  public void setup() {
    user = new User();
    user.setUsername("ajay101");
    user.setEmail("ajay@gmail.com");
    user.setName("Ajay");
    user.setPassword("password");
    user.setId(1);
  }

  @DisplayName("View the profile of a User")
  @Test
  public void givenUser_whenSaveUser_thenReturnUser() throws UserNotFoundException {
    given(userRepository.findByEmail(user.getEmail()))
        .willReturn(Optional.empty());

    when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
      return user;
    });

    when(userRepository.findById(any(Integer.class))).thenAnswer(invocation -> {
      return Optional.of(user);
    });

    User savedUser = userService.getUser(user.getId());

    assertThat(savedUser).isNotNull();
    assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
    assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
    assertThat(savedUser.getName()).isEqualTo(user.getName());
  }

}
