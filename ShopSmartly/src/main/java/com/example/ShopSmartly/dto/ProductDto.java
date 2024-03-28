package com.example.ShopSmartly.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {

    private String productName;
    private String productDescription;
    private Integer price;
    private String category;
    private MultipartFile image;
    private byte[] returnedImage;

}
