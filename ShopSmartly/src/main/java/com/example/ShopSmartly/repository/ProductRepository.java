package com.example.ShopSmartly.repository;

import com.example.ShopSmartly.entity.Product;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product>findByOrderById();
}
