package com.example.ShopSmartly.services;

import com.example.ShopSmartly.dto.ProductDto;
import com.example.ShopSmartly.entity.Product;
import com.example.ShopSmartly.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        // Optionally, you can perform any setup here before each test method
    }

    @Test
    public void testSaveProducts_Success() throws IOException {
        // Prepare test data
        ProductDto productDto = new ProductDto();
        productDto.setProductName("Test Product");
        productDto.setProductDescription("Description");
        productDto.setPrice(100L);
        productDto.setCategory("Test Category");
        MockMultipartFile imageFile = new MockMultipartFile("image", "test-image.jpg", "image/jpeg", "test image content".getBytes());
        productDto.setImage(imageFile);

        // Execute the method to test
        ResponseEntity<String> responseEntity = productService.saveProducts(productDto);

        // Verify the product is registered successfully
        assertEquals("Product is registered", responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verify the product is saved in the database
        assertTrue(productRepository.existsByProductName("Test Product"));
    }

    @Test
    public void testSaveProducts_ProductWithSameImageExists() throws IOException {
        // Prepare test data
        ProductDto productDto = new ProductDto();
        productDto.setProductName("Test Product 1");
        productDto.setProductDescription("Description");
        productDto.setPrice(50L);
        productDto.setCategory("Test Category");
        MockMultipartFile imageFile = new MockMultipartFile("image2", "testing2.jpg", "image/jpeg", "test image content".getBytes());
        productDto.setImage(imageFile);
        productService.saveProducts(productDto); // Save product with the same image

        ProductDto productDto2 = new ProductDto();
        productDto2.setProductName("Test Product 2");
        productDto2.setProductDescription("Description");
        productDto2.setPrice(150L);
        productDto2.setCategory("Test Category");
        productDto2.setImage(imageFile); // Use the same image for another product

        // Execute the method to test
        ResponseEntity<String> responseEntity = productService.saveProducts(productDto2);

        // Verify the product with the same image already exists response
        assertEquals("Product with the same image already exists", responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteProduct_Success() {
        // For instance, test data with product id -> 3 already exist

        // Execute the method to test
        productService.deleteProduct(3L);

        // Verify that the product with ID 1 doesn't exist after deletion
        Optional<Product> optionalProduct = productRepository.findById(3L);
        assertEquals(false, optionalProduct.isPresent(), "Product with ID 1 should not exist after deletion");
    }

    @Test
    public void testDeleteProduct_ProductNotFound() {
        // Execute the method to test and verify that it throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(100L));
    }
}
