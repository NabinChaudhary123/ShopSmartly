package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.dto.OrderDto;
import com.example.ShopSmartly.services.CartService;
import com.example.ShopSmartly.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/orderByUserId/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderByUserId(@PathVariable Long userId){
        List<OrderDto> orderDtoList = orderService.getOrderByUserId(userId);
        return ResponseEntity.ok(orderDtoList);
    }

    @GetMapping("/allOrders")
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        List<OrderDto> allOrders = orderService.getAllOrders();
        return ResponseEntity.ok(allOrders);
    }
}
