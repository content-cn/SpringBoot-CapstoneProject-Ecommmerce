package com.springboot.ecommerceapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    @Column(name = "product_id")
    private Integer productId;

    private Date createdDate;

//    @ManyToOne
//    @JoinColumn(name="product_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private Product product;

    private int quantity;


}
