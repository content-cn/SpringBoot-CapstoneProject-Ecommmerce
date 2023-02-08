package com.springboot.ecommerceapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private String firstName;

    private String lastName;

    private String mobile;

    private String pincode;

    private String glat;

    private String street;

    private String state;

    private String city;
}
