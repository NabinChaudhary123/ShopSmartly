package com.example.ShopSmartly.repository;

import com.example.ShopSmartly.dto.ProductDto;
import com.example.ShopSmartly.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByCategory(String category);

    boolean existsByImage(byte[] bytes);

}
