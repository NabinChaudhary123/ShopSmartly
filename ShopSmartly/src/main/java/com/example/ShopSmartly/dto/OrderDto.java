package com.example.ShopSmartly.dto;

import com.example.ShopSmartly.entity.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private Long id;

    private String description;
    private String address;
    private String paymentType;
    private Date date;
    private Long price;
    private Long totalAmount;
    private OrderStatus orderStatus;
    private String fullName;
    private List<CartItemsDto> cartItems;


}
