package com.example.ShopSmartly.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface ScrapedProductService {

    CompletableFuture<List<Map<String, String>>> fetchFreePeopleAsync(String query);
    CompletableFuture<List<Map<String,String>>> fetchSaltSurfAsync(String query);
    CompletableFuture<List<Map<String, String>>> fetchSnapDealAsync(String query);
    CompletableFuture<List<Map<String, String>>> fetchHMAsync(String query);
    CompletableFuture<List<Map<String, String>>> fetchFashionJunkeeAsync(String query);
    CompletableFuture<List<Map<String, String>>> fetchAberCrombieAsync(String query);
    CompletableFuture<List<Map<String, String>>> fetchMacysAsync(String query);
    List<Map<String, String>> scrapeMacys(String query) throws IOException;
//    List<Map<String, String>> fetchFreePeople(String query) throws IOException;
//    List<Map<String, String>> scrapealoYoga(String query) throws IOException;
}
