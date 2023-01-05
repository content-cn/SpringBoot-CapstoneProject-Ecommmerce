package com.springboot.ecommerceapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer quantity;

    private Double price;

    private Date createdDate;

//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Integer orderId;

    //@OneToOne
//    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Integer productId;
}
