package com.example.ShopSmartly.repository;

import com.example.ShopSmartly.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    @Query("SELECT i FROM Invoice i JOIN FETCH i.order")
    List<Invoice> findAllWithOrder();
}
