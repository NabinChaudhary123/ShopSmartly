package com.example.ShopSmartly.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@Data
public class ProductResponse {

    private List<Map<String, String>> hmProducts;
    private List<Map<String, String>> fjProducts;
    private List<Map<String, String>> abcProducts;
    private List<Map<String, String>> snapdealProducts;
    private List<Map<String, String>> saltSurfProducts;
    private List<Map<String, String>> freePeopleProducts;
    private List<Map<String, String>> macysProducts;

    public ProductResponse(List<Map<String, String>> hmProducts, List<Map<String, String>> fjProducts,
                           List<Map<String, String>> abcProducts, List<Map<String, String>> snapdealProducts,
                           List<Map<String, String>> saltSurfProducts, List<Map<String, String>> freePeopleProducts,
                           List<Map<String, String>> macysProducts) {
        this.hmProducts = hmProducts;
        this.fjProducts = fjProducts;
        this.abcProducts = abcProducts;
        this.snapdealProducts = snapdealProducts;
        this.saltSurfProducts = saltSurfProducts;
        this.freePeopleProducts = freePeopleProducts;
        this.macysProducts = macysProducts;
    }

//    public String comparePrices(){
//        double ebayTotalPrice = 0;
//        for(Map<String,String>ebay:ebayProducts){
//            ebayTotalPrice +=Double.parseDouble(ebay.get("price").replaceAll("[^\\d]",""));
//        }
//        double ebayAveragePrice = ebayTotalPrice/ebayProducts.size();
//
//        double etsyTotalPrice = 0;
//        for(Map<String,String>etsy:etsyProducts){
//            etsyTotalPrice +=Double.parseDouble(etsy.get("price").replaceAll("[^\\d]",""));
//        }
//        double etsyAveragePrice = etsyTotalPrice/ebayProducts.size();
//
//        if(ebayAveragePrice<etsyAveragePrice){
//            return "Ebay has lower average price that etsy.";
//        }
//        else if(ebayAveragePrice>etsyAveragePrice){
//            return "Etsy has lower average prices than ebay";
//        }
//        else{
//            return "The average prices on ebay and etsy are same";
//        }
//    }
}
