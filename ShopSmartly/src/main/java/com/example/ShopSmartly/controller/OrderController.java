package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.dto.OrderDto;
import com.example.ShopSmartly.entity.Order;
import com.example.ShopSmartly.services.CartService;
import com.example.ShopSmartly.services.OrderService;
import com.example.ShopSmartly.services.PdfGeneratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final PdfGeneratorService pdf;

    public OrderController(OrderService orderService, PdfGeneratorService pdf) {
        this.orderService = orderService;
        this.pdf = pdf;
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

    @GetMapping("/allOrdersDesc")
    public ResponseEntity<List<OrderDto>>getAllOrdersDesc(){
        List<OrderDto> allOrdersDesc = orderService.getAllOrdersDesc();
        return ResponseEntity.ok(allOrdersDesc);
    }

    @GetMapping("/generatePDF")
    public ResponseEntity<List<OrderDto>>generatePdf() throws IOException {
        List<OrderDto> allOrders = orderService.getAllOrders();
        try{
            String filePath = pdf.generatePdf(allOrders);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(allOrders);
    }
}
