package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.dto.AddProductInCartDto;
import com.example.ShopSmartly.dto.CartItemsDto;
import com.example.ShopSmartly.dto.OrderDto;
import com.example.ShopSmartly.dto.PlaceOrderDto;
import com.example.ShopSmartly.entity.Order;
import com.example.ShopSmartly.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/postCart")
    public ResponseEntity<?> addProductToCart(@RequestBody CartItemsDto cartDto){
        return cartService.addProductToCart(cartDto);
//        return new ResponseEntity<>(cartService.addProductToCart(cartDto),HttpStatus.CREATED);
    }

    @GetMapping("/getCartByUserId/{userId}")
    public ResponseEntity<OrderDto> getCartByUserId(@PathVariable Long userId){
        OrderDto orderDto = cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @PostMapping("/addQuantity")
    public ResponseEntity<OrderDto> increaseProductQuantity(@RequestBody AddProductInCartDto addProductInCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQuantity(addProductInCartDto));
    }

    @PostMapping("/decreaseQuantity")
    public ResponseEntity<OrderDto> decreaseProductQuantity(@RequestBody AddProductInCartDto addProductInCartDto){
        OrderDto orderDto = cartService.decreaseProductQuantity(addProductInCartDto);
        return new ResponseEntity<>(orderDto,HttpStatus.OK);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto){
        OrderDto orderDto = cartService.placeOrder(placeOrderDto);
        if(orderDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
        }
    }
}
