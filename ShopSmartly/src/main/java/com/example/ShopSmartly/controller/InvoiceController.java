package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.services.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/totalInvoices")
    public ResponseEntity<?>getAllInvoices(){
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }
}
