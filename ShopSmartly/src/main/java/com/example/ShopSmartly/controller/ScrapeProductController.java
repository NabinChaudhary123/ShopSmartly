package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.services.ScrapedProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/web")
public class ScrapeProductController {

    private final ScrapedProductService productService;

    public ScrapeProductController(ScrapedProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/scrape")
    public ResponseEntity<?> scrapeProducts(@RequestParam String query) throws IOException {
        return ResponseEntity.ok(productService.scrapeWebForProducts(query));
    }
}
