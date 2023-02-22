package com.springboot.ecommerceapp.unittests;

import com.springboot.ecommerceapp.exception.UserNotFoundException;
import com.springboot.ecommerceapp.models.Address;
import com.springboot.ecommerceapp.repositories.AddressRepository;
import com.springboot.ecommerceapp.services.AddressService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AddressServiceTests {

  @Mock
  private AddressRepository addressRepository;

  @InjectMocks
  private AddressService addressService;

  private Address address;

  @Before
  public void setup() {
    address = new Address();
    address.setId(1);
    address.setFirstName("Ajay");
    address.setLastName("Yadav");
    address.setPincode("123401");
    address.setCity("Rewari");
  }

  @DisplayName("Add a new user address")
  @Test
  public void add_user_address() throws UserNotFoundException {
    given(addressRepository.findById(address.getId()))
        .willReturn(Optional.empty());

    when(addressRepository.save(any(Address.class))).thenAnswer(invocation -> {
      return address;
    });

    Address savedAddress = addressService.addAddress(address);

    assertThat(savedAddress).isNotNull();
    assertThat(savedAddress.getFirstName()).isEqualTo(address.getFirstName());
    assertThat(savedAddress.getCity()).isEqualTo(address.getCity());
    assertThat(savedAddress.getMobile()).isEqualTo(address.getMobile());
  }

  @DisplayName("Fetch an address based on ID")
  @Test
  public void givenUser_whenSaveUser_thenReturnUser() throws UserNotFoundException {
    given(addressRepository.findById(address.getId()))
        .willReturn(Optional.empty());

    when(addressRepository.save(any(Address.class))).thenAnswer(invocation -> {
      return address;
    });

    when(addressRepository.findById(any(Integer.class))).thenAnswer(invocation -> {
      return Optional.of(address);
    });

    Optional<Address> optionalAddress = addressService.getAddress(address.getId());
    assertThat(optionalAddress.isPresent()).isTrue();

    Address savedAddress = optionalAddress.get();

    assertThat(savedAddress).isNotNull();
    assertThat(savedAddress.getFirstName()).isEqualTo(address.getFirstName());
    assertThat(savedAddress.getCity()).isEqualTo(address.getCity());
    assertThat(savedAddress.getMobile()).isEqualTo(address.getMobile());
  }

}
