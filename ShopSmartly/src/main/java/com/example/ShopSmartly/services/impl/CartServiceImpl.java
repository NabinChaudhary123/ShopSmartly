package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.dto.*;
import com.example.ShopSmartly.entity.*;
import com.example.ShopSmartly.repository.CartItemRepository;
import com.example.ShopSmartly.repository.OrderRepository;
import com.example.ShopSmartly.repository.ProductRepository;
import com.example.ShopSmartly.repository.UserRepository;
import com.example.ShopSmartly.services.CartService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
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
    public ResponseEntity<?> addProductToCart(CartItemsDto cartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(cartDto.getUserId(), OrderStatus.Pending);

        List<CartItems> cartItems = cartItemRepository.findByUserIdAndOrderId(cartDto.getUserId(), activeOrder.getId());
        boolean productExistInCart = cartItems.stream()
                .anyMatch(cartItem -> cartItem.getProduct().getId().equals(cartDto.getProductId()));

        if (productExistInCart) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already exist");
        } else {
            Optional<Product> optionalProduct = productRepository.findById(cartDto.getProductId());
            Optional<UserEntity> optionalUser = userRepository.findById(cartDto.getUserId());

            if (!optionalProduct.isPresent() || !optionalUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            }
            Product product = optionalProduct.get();
            UserEntity user = optionalUser.get();
            CartItems cart = new CartItems();
            cart.setProduct(product);
            cart.setPrice(product.getPrice());
            cart.setQuantity(1L);
            cart.setUser(user);
            cart.setOrder(activeOrder);
            CartItems updatedCart = cartItemRepository.save(cart);

            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
            activeOrder.setPrice(activeOrder.getPrice() + cart.getPrice());
            activeOrder.getCartItems().add(cart);
            orderRepository.save(activeOrder);

            CartItemsDto updatedCartItemDto = new CartItemsDto();
            updatedCartItemDto.setId(updatedCart.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(updatedCartItemDto);
        }
    }

    @Override
    public OrderDto getCartByUserId(Long userId) {

        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemsDto> cartItemsDtos = activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());
        OrderDto orderDto = new OrderDto();
        orderDto.setPrice(activeOrder.getPrice());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtos);
        return orderDto;
    }

    @Override
    public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());

        Optional<CartItems> optionalCartItems = cartItemRepository.findByUserIdAndProductIdAndOrderId(addProductInCartDto.getUserId(),
                addProductInCartDto.getProductId(), activeOrder.getId());


        if(optionalProduct.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItems = optionalCartItems.get();
            Product product = optionalProduct.get();


            activeOrder.setPrice(activeOrder.getPrice()+product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount()+product.getPrice());

            cartItems.setQuantity(cartItems.getQuantity()+1);

            cartItemRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    @Override
    public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());

        Optional<CartItems> optionalCartItems = cartItemRepository.findByUserIdAndProductIdAndOrderId(addProductInCartDto.getUserId(),
                addProductInCartDto.getProductId(), activeOrder.getId());

        if(optionalProduct.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItems = optionalCartItems.get();
            Product product = optionalProduct.get();

//            cartItems.setTotalPrice(cartItems.getPrice()/ cartItems.getQuantity());

            activeOrder.setPrice(activeOrder.getPrice()-product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());

            cartItems.setQuantity(cartItems.getQuantity()-1);

            cartItemRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

}
