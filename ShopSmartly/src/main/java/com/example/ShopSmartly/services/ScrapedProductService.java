package com.example.ShopSmartly.services;

import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ScrapedProductService {

    List<Map<String, String>> fetchSaltSurf(String query) throws IOException;
    List<Map<String, String>> fetchSnapDeal(String query) throws IOException;
    List<Map<String, String>> scrapeHM(String query) throws IOException;
    List<Map<String, String>> scrapeFashionJunkee(String query) throws IOException;
    List<Map<String, String>> scrapeAbercrombie(String query) throws IOException;
    List<Map<String, String>> scrapeMacys(String query) throws IOException;
    List<Map<String, String>> fetchFreePeople(String query) throws IOException;
//    List<Map<String, String>> scrapealoYoga(String query) throws IOException;
}
