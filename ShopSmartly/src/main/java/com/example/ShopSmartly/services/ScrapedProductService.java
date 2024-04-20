package com.example.ShopSmartly.services;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ScrapedProductService {

    ResponseEntity<?>scrapeEbay(String query) throws IOException;
    ResponseEntity<?> scrapeEtsy(String query) throws IOException;
}
