package com.springboot.ecommerceapp.controller;

import com.springboot.ecommerceapp.dto.CartItemRequestDto;
import com.springboot.ecommerceapp.dto.CartResponseDto;
import com.springboot.ecommerceapp.exception.CartItemNotExistException;
import com.springboot.ecommerceapp.models.Address;
import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public void addAddress(@RequestBody @Valid Address address) {
        addressService.addAddress(address);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable("id") Integer id) {
        Optional<Address> addressOptional = addressService.getAddress(id);
        if (addressOptional.isPresent()) {
            return ResponseEntity.ok().body(addressOptional.get());
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/byUser/{userId}")
    public List<Address> getAddressByUser(@PathVariable("userId") Integer userId) {
        return addressService.getAllAddressForUser(userId);
    }

}
