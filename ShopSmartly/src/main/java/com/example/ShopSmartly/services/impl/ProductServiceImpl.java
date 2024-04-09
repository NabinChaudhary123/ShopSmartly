package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.dto.ProductDto;
import com.example.ShopSmartly.entity.Product;
import com.example.ShopSmartly.repository.ProductRepository;
import com.example.ShopSmartly.services.ProductService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {

        return productRepository.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> saveProducts(ProductDto productDto) throws IOException {

           Product product = new Product();
           product.setProductName(productDto.getProductName());
           product.setProductDescription(productDto.getProductDescription());
           product.setPrice(productDto.getPrice());
           product.setCategory(productDto.getCategory());
           product.setImage(productDto.getImage().getBytes());
            productRepository.save(product);
           return new ResponseEntity<>("Product is registered", HttpStatus.CREATED);


    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        return productRepository.findAllByCategory(category).stream().map(Product::getProductDto).collect(Collectors.toList());
    }
}
