package com.springboot.ecommerceapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

// @Entity is a marker interface that implies that this entity can be mapped to a table.
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Date createdDate;

    private Double totalPrice;

    private String sessionId;

//    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
//    private List<OrderItem> orderItems;

//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;

    private Integer userId;
}
