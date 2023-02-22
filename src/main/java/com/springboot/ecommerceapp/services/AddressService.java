
package com.springboot.ecommerceapp.services;

import com.springboot.ecommerceapp.dto.CartItemRequestDto;
import com.springboot.ecommerceapp.dto.CartItemResponseDto;
import com.springboot.ecommerceapp.dto.CartResponseDto;
import com.springboot.ecommerceapp.dto.ProductDto;
import com.springboot.ecommerceapp.exception.CartItemNotExistException;
import com.springboot.ecommerceapp.models.Address;
import com.springboot.ecommerceapp.models.CartItem;
import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.repositories.AddressRepository;
import com.springboot.ecommerceapp.repositories.CartItemRepository;
import com.springboot.ecommerceapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

   public List<Address> getAllAddressForUser(Integer userId) {
        return addressRepository.findAllByUserId(userId);
   }

   public Optional<Address> getAddress(Integer id) {
      return addressRepository.findById(id);
   }
}
