package com.example.ShopSmartly.services;

import com.example.ShopSmartly.dto.OrderDto;
import com.example.ShopSmartly.dto.PlaceOrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getOrderByUserId(Long userId);
    List<OrderDto> getAllOrders();
    List<OrderDto> getAllOrdersDesc();
    OrderDto placeOrder(PlaceOrderDto placeOrderDto);
}
