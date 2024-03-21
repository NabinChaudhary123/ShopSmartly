package com.example.ShopSmartly.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private Integer contact;
    private String password;
    @CreationTimestamp
    @Column(name = "created_At", nullable = false, updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "update_At")
    private Date updatedAt;
}
