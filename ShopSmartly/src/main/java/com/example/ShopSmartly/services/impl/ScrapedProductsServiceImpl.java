package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.services.ScrapedProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScrapedProductsServiceImpl implements ScrapedProductService {

    @Override
    public ResponseEntity<?> scrapeWebForProducts(String query) throws IOException {

        StringBuilder result =new StringBuilder();
        try{
            Document doc = Jsoup.connect("https://www.shein.com/search/?q=" + query).get();
            //Extract item elements
            Elements items = doc.select(".c-goods-list__item");

            List<Map<String, String>> productList = new ArrayList<>();

            //Iterate over items and extract data
            for(Element item:items){
                String title = item.select(".c-goods_info_title").text();
                String price = item.select(".c-goods_price").text();
                String productLink = item.select(".c-goods_info_title a").attr("href");
                String imageURL = item.select(".c-goods_img").attr("src");

                // Create a map to store title and price
                Map<String, String> product = new HashMap<>();
                product.put("title",title);
                product.put("price", price);
                product.put("link",productLink);
                product.put("imageURL",imageURL);
                productList.add(product);
            }
            return ResponseEntity.ok().body(productList);
        }
        catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }




//    @Override
//    public ResponseEntity<?> scrapeWebForProducts(String query) throws IOException {
//        StringBuilder result =new StringBuilder();
//        try{
//            Document doc = Jsoup.connect("https://www.etsy.com/search?q="+query).get();
//            //Extract item elements
//            Elements items = doc.select(".v2-listing-card");
//
//            List<Map<String, String>> productList = new ArrayList<>();
//
//            //Iterate over items and extract data
//            for(Element item:items){
//                String title =item.select("h3").text();
//                String price = item.select(".currency-value").text();
//                String productLink = item.select("a").attr("href");
//                String imageURL = item.select("img").attr("src");
//
//                // Create a map to store title and price
//                Map<String, String> product = new HashMap<>();
//                product.put("title",title);
//                product.put("price", price);
//                product.put("link",productLink);
//                product.put("imageURL",imageURL);
//                productList.add(product);
//            }
//            return ResponseEntity.ok().body(productList);
//        }
//        catch (IOException e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
//        }
//    }
}
