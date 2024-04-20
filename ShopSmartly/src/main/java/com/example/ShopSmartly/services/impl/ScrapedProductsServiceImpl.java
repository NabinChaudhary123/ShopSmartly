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

    //Ebay
    @Override
    public ResponseEntity<?> scrapeEbay(String query) throws IOException {
        StringBuilder result =new StringBuilder();
        try{
            Document doc = Jsoup.connect("https://www.ebay.com/sch/i.html?_nkw="+query)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36")
                    .get();
            //Extract item elements
//            Elements items = doc.select("div.s-item__info");
                Elements items = doc.select("li.s-item");

            List<Map<String, String>> productList = new ArrayList<>();

            //Iterate over items and extract data
            for(Element item:items){
                String title = item.select(".s-item__title").text().trim();
                String price = item.select("span.s-item__price").text().trim();
                String productLink = item.select("a.s-item__link").attr("href").trim();
                String imageURL = item.select(".s-item__image img").attr("src");



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

// free people
//    @Override
//    public ResponseEntity<?> scrapeWebForProducts(String query) throws IOException {
//        StringBuilder result =new StringBuilder();
//        try{
//            Document doc = Jsoup.connect("https://www.freepeople.com/search?q="+query)
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36")
//                    .get();
//            //Extract item elements
//            Elements items = doc.select(".c-pwa-tile-grid-tile");
//
//            List<Map<String, String>> productList = new ArrayList<>();
//
//            //Iterate over items and extract data
//            for(Element item:items){
//                String title = item.select(".o-pwa-product-tile__heading").text();
//                String price = item.select(".c-pwa-product-price__current").text();
//                String imageURL = item.select(".o-pwa-product-tile__media img").attr("src");
//                String productLink = item.select(".c-pwa-link").attr("href");
//
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

    //salt surf less products
//    @Override
//    public ResponseEntity<?> scrapeWebForProducts(String query) throws IOException {
//        StringBuilder result =new StringBuilder();
//        try{
//            Document doc = Jsoup.connect("https://www.saltsurf.com/search?type=product&q="+query).get();
//            //Extract item elements
//            Elements items = doc.select(".product-block");
//
//            List<Map<String, String>> productList = new ArrayList<>();
//
//            //Iterate over items and extract data
//            for(Element item:items){
//                String title = item.select(".title").text();
////                String availability = item.select(".bold").text();
//                String price = item.select(".theme-money").first().text();
////                String originalPrice = item.select(".was-price.theme-money").first().text();
//                String imageURL = item.select(".rimage__image").attr("src");
//                String productLink = "https://www.saltsurf.com/"+item.select("a.image-inner").attr("href");
//
//
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


    // Snapdeal

//    @Override
//    public ResponseEntity<?> scrapeWebForProducts(String query) throws IOException {
//
//        StringBuilder result =new StringBuilder();
//        try{
//            Document doc = Jsoup.connect("https://snapdeal.com/search?keyword=" + query).get();
//            //Extract item elements
//            Elements items = doc.select(".product-tuple-listing");
//
//            List<Map<String, String>> productList = new ArrayList<>();
//
//            //Iterate over items and extract data
//            for(Element item:items){
//                String title = item.select(".product-title").text();
//                String price = item.select(".product-price").text();
//                String productLink = item.select(".product-desc-rating").text();
////                String imageURL = item.select("img").attr("src");
//                String imageURL;
//                if(item.select(".product-image").hasClass("wooble")){
//                    imageURL = item.select(".product-tuple-image img").attr("src");
//                }
//                else{
//                    imageURL = item.select("img").attr("src");
//                }
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


//                 ETSY.com

    @Override
    public ResponseEntity<?> scrapeEtsy(String query) throws IOException {
        StringBuilder result =new StringBuilder();
        try{
            Document doc = Jsoup.connect("https://www.etsy.com/search?q="+query).get();
            //Extract item elements
            Elements items = doc.select(".v2-listing-card");

            List<Map<String, String>> productList = new ArrayList<>();

            //Iterate over items and extract data
            for(Element item:items){
                String title =item.select("h3").text();
                String price = item.select(".currency-value").text();
                String productLink = item.select("a").attr("href");
                String imageURL = item.select("img").attr("src");

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
}
