package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.dto.CartDto;
import com.example.ShopSmartly.dto.CartItemsDto;
import com.example.ShopSmartly.dto.OrderDto;
import com.example.ShopSmartly.entity.*;
import com.example.ShopSmartly.repository.CartItemRepository;
import com.example.ShopSmartly.repository.OrderRepository;
import com.example.ShopSmartly.repository.ProductRepository;
import com.example.ShopSmartly.repository.UserRepository;
import com.example.ShopSmartly.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<?> addProductToCart(CartDto cartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(cartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItems> optionalCartItems = cartItemRepository.findByUserIdAndProductIdAndOrderId(
                cartDto.getProductId(), cartDto.getUserId(), activeOrder.getId());

        if(optionalCartItems.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        else {
            Optional<Product> optionalProduct = productRepository.findById(cartDto.getProductId());
            Optional<UserEntity> optionalUser = userRepository.findById(cartDto.getUserId());

            if(optionalProduct.isPresent() && optionalUser.isPresent()){
                CartItems cart = new CartItems();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(Long.valueOf(optionalProduct.get().getPrice()));
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                CartItems updatedCart = cartItemRepository.save(cart);
                activeOrder.setTotalAmount(activeOrder.getTotalAmount()+cart.getPrice());
                activeOrder.setPrice(activeOrder.getPrice()+cart.getPrice());
                activeOrder.getCartItems().add(cart);

                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cart);
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }

    }

    @Override
    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemsDto> cartItemsDtos = activeOrder.getCartItems().stream().map(CartItems::getCartItemsDto).collect(Collectors.toList());
        OrderDto orderDto = new OrderDto();
        orderDto.setPrice(activeOrder.getPrice());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtos);
        return orderDto;
    }
}
