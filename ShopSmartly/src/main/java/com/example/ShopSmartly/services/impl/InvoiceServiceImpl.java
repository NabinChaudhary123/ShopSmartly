package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.entity.Invoice;
import com.example.ShopSmartly.repository.InvoiceRepository;
import com.example.ShopSmartly.services.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAllWithOrder();
    }
}
