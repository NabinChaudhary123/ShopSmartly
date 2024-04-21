package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.dto.ProductResponse;
import com.example.ShopSmartly.services.ScrapedProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web")
public class ScrapeProductController {

    private final ScrapedProductService productService;

    public ScrapeProductController(ScrapedProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/scrape")
    public ProductResponse scrapeProducts(@RequestParam String query) throws IOException {
       try{
           List<Map<String, String>> etsyProducts = productService.scrapeEtsy(query);
           List<Map<String, String>> ebayProducts = productService.scrapeEbay(query);

           return new ProductResponse(ebayProducts,etsyProducts);

       }
       catch (IOException e){
           e.printStackTrace();
           return null;
       }
    }

    @GetMapping("/scrapy")
    public ResponseEntity<?> scrape(@RequestParam String query)throws IOException{
        return ResponseEntity.ok(productService.scrapealoYoga(query));
    }

}
