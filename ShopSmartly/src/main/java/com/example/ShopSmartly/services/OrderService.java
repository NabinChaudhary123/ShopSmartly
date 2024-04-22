package com.example.ShopSmartly.services;

import com.example.ShopSmartly.dto.OrderDto;
import com.example.ShopSmartly.dto.PlaceOrderDto;
import com.example.ShopSmartly.entity.Order;

import java.util.List;

public interface OrderService {


    List<OrderDto> getOrderByUserId(Long userId);

    // Submitted orders
    List<OrderDto> getAllOrders();

    // Recent Orders
    List<OrderDto> getAllOrdersDesc();
    OrderDto placeOrder(PlaceOrderDto placeOrderDto);

    // get All Orders - {pending | Submitted}
    List<Order> getTotalOrders();

    // get pending orders
    List<OrderDto> getPendingOrders();
}
