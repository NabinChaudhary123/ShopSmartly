package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.dto.ProductDto;
import com.example.ShopSmartly.entity.Product;
import com.example.ShopSmartly.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/listProducts")
    public ResponseEntity<List<ProductDto>> allProducts(){
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>>getProductsByCategory(@RequestParam String category){
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @PostMapping("/registerProducts")
    public ResponseEntity<?> registerProducts(@ModelAttribute ProductDto productDto) throws IOException{
        return ResponseEntity.ok(productService.saveProducts(productDto));
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


}
