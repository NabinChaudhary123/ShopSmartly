package com.example.ShopSmartly.services;

import com.example.ShopSmartly.dto.ProductDto;
import com.example.ShopSmartly.entity.Product;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByCategory(String category);
    ResponseEntity<String> saveProducts(ProductDto productDto) throws IOException;
    void deleteProduct(Long id);

}
