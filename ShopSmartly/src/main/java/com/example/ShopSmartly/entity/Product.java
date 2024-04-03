package com.example.ShopSmartly.entity;

import com.example.ShopSmartly.dto.ProductDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String productDescription;
    private Integer price;
    private String category;

    @Column(columnDefinition = "longblob")
    private byte[] image;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_At")
    private Date updatedAt;

    public ProductDto getProductDto(){

        ProductDto productDto = new ProductDto();
        productDto.setProductId(id);
        productDto.setProductName(productName);
        productDto.setProductDescription(productDescription);
        productDto.setCategory(category);
        productDto.setPrice(price);
        productDto.setReturnedImage(image);
        return productDto;
    }
}
