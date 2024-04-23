package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.dto.OrderDto;
import com.example.ShopSmartly.dto.PlaceOrderDto;
import com.example.ShopSmartly.entity.Invoice;
import com.example.ShopSmartly.entity.Order;
import com.example.ShopSmartly.entity.OrderStatus;
import com.example.ShopSmartly.entity.UserEntity;
import com.example.ShopSmartly.repository.InvoiceRepository;
import com.example.ShopSmartly.repository.OrderRepository;
import com.example.ShopSmartly.repository.UserRepository;
import com.example.ShopSmartly.services.KhaltiService;
import com.example.ShopSmartly.services.OrderService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, InvoiceRepository invoiceRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.invoiceRepository = invoiceRepository;

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

    public List<Order> getTotalOrders(){
        return orderRepository.findAll();
    }

    @Override
    public List<OrderDto> getPendingOrders() {
        return orderRepository.findAllByOrderStatus(OrderStatus.Pending).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrdersDesc() {
        List<Order> sortedOrders = orderRepository.findAllByOrderStatusOrderByDateDesc(OrderStatus.Submitted);
        return sortedOrders.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto placeOrder(PlaceOrderDto placeOrderDto){
        Order existingOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        Optional<UserEntity> optionalUser = userRepository.findById(placeOrderDto.getUserId());
        if(optionalUser.isPresent()){
            existingOrder.setDescription(placeOrderDto.getDescription());
            existingOrder.setAddress(placeOrderDto.getAddress());
            existingOrder.setDate(new Date());
            existingOrder.setPaymentType(placeOrderDto.getPayment());
            existingOrder.setTotalAmount(existingOrder.getTotalAmount());
            existingOrder.setOrderStatus(OrderStatus.Submitted);
            orderRepository.save(existingOrder);

            Invoice invoice = generateInvoice(existingOrder);
            invoiceRepository.save(invoice);

            Order order = new Order();
            order.setPrice(0L);
            order.setTotalAmount(0L);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);

            return order.getOrderDto();
        }
        return null;
    }

    public Invoice generateInvoice(Order order){
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(generateInvoiceNum());
        invoice.setTotalAmount(order.getTotalAmount());
        invoice.setInvoiceDate(new Date());
        invoice.setOrder(order);

        return invoice;
    }

    private String generateInvoiceNum() {
        // Implement logic to generate a unique invoice number
        // For example: you can concatenate the current date with a random number
        return "INV-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-" + UUID.randomUUID().toString().substring(0, 8);
    }


}
