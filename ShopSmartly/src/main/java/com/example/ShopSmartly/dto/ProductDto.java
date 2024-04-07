package com.example.ShopSmartly.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {
    private Long productId;
    private String productName;
    private String productDescription;
    private Long price;
    private String category;
    private MultipartFile image;
    private byte[] returnedImage;

}
