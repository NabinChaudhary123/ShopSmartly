package com.example.ShopSmartly.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
@Getter
@Setter
@Data
public class ProductResponse {

    private List<Map<String, String>> ebayProducts;
    private List<Map<String, String>> etsyProducts;

    public ProductResponse(List<Map<String, String>> ebayProducts, List<Map<String, String>> etsyProducts) {
        this.ebayProducts = ebayProducts;
        this.etsyProducts = etsyProducts;
    }
    public String comparePrices(){
        double ebayTotalPrice = 0;
        for(Map<String,String>ebay:ebayProducts){
            ebayTotalPrice +=Double.parseDouble(ebay.get("price").replaceAll("[^\\d]",""));
        }
        double ebayAveragePrice = ebayTotalPrice/ebayProducts.size();

        double etsyTotalPrice = 0;
        for(Map<String,String>etsy:etsyProducts){
            etsyTotalPrice +=Double.parseDouble(etsy.get("price").replaceAll("[^\\d]",""));
        }
        double etsyAveragePrice = etsyTotalPrice/ebayProducts.size();

        if(ebayAveragePrice<etsyAveragePrice){
            return "Ebay has lower average price that etsy.";
        }
        else if(ebayAveragePrice>etsyAveragePrice){
            return "Etsy has lower average prices than ebay";
        }
        else{
            return "The average prices on ebay and etsy are same";
        }
    }
}
