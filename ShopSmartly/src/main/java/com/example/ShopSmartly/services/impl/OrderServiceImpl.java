package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.dto.OrderDto;
import com.example.ShopSmartly.entity.Order;
import com.example.ShopSmartly.entity.OrderStatus;
import com.example.ShopSmartly.repository.OrderRepository;
import com.example.ShopSmartly.services.OrderService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDto> getOrderByUserId(Long userId) {
        return orderRepository.findAllByUserIdAndOrderStatus(userId, OrderStatus.Submitted)
                .stream()
                .map(Order::getOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAllByOrderStatus(OrderStatus.Submitted).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrdersDesc() {
        List<Order> sortedOrders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
        return sortedOrders.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }


}
