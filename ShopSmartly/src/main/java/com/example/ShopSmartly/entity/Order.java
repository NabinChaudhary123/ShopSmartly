package com.example.ShopSmartly.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.stream.Stream;

@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String address;
    private String paymentType;
    private Date date;
    private Long price;
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
}
