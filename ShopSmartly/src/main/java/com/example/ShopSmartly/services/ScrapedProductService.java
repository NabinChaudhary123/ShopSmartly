package com.example.ShopSmartly.services;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ScrapedProductService {

    ResponseEntity<?> scrapeWebForProducts(String query) throws IOException;
}
