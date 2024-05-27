package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.dto.ProductResponse;
import com.example.ShopSmartly.services.ScrapedProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/web")
public class ScrapeProductController {

    private final ScrapedProductService productService;

    public ScrapeProductController(ScrapedProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/scrape")
    public ProductResponse scrapeProducts(@RequestParam String query) {
        try{
            CompletableFuture<List<Map<String, String>>> hmProductsFuture = productService.fetchHMAsync(query);
            CompletableFuture<List<Map<String, String>>> fjProductsFuture = productService.fetchFashionJunkeeAsync(query);
            CompletableFuture<List<Map<String, String>>> abcProductsFuture = productService.fetchAberCrombieAsync(query);
            CompletableFuture<List<Map<String, String>>> snapdealProductsFuture = productService.fetchSnapDealAsync(query);
            CompletableFuture<List<Map<String, String>>> saltSurfProductsFuture = productService.fetchSaltSurfAsync(query);
            CompletableFuture<List<Map<String, String>>> freePeopleProductsFuture = productService.fetchFreePeopleAsync(query);
            CompletableFuture<List<Map<String, String>>> macysProductsFuture = productService.fetchMacysAsync(query);

            CompletableFuture.allOf(hmProductsFuture, fjProductsFuture, abcProductsFuture, snapdealProductsFuture, saltSurfProductsFuture, freePeopleProductsFuture, macysProductsFuture).join();
            List<Map<String, String>> hmProducts = hmProductsFuture.get();
            List<Map<String, String>> fjProducts = fjProductsFuture.get();
            List<Map<String, String>> abcProducts = abcProductsFuture.get();
            List<Map<String, String>> snapdealProducts = snapdealProductsFuture.get();
            List<Map<String, String>> saltSurfProducts = saltSurfProductsFuture.get();
            List<Map<String, String>> freePeopleProducts = freePeopleProductsFuture.get();
            List<Map<String, String>> macysProducts = macysProductsFuture.get();

            return new ProductResponse(hmProducts,fjProducts,abcProducts,snapdealProducts,saltSurfProducts,freePeopleProducts, macysProducts);
        }
        catch (InterruptedException |ExecutionException e){
            e.printStackTrace();
            return null;
        }


    }

//    @GetMapping("/scrapy")
//    public ResponseEntity<?> scrape(@RequestParam String query)throws IOException {
//        return ResponseEntity.ok(productService.scrapeMacys(query));
//    }

}
