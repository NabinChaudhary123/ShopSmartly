package com.example.ShopSmartly.entity;

import com.example.ShopSmartly.dto.OrderDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String address;
    private String paymentType;
    private Date date;
    private Long price;
    private Long totalAmount;
    private OrderStatus orderStatus;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @JsonIgnore
    private List<CartItems> cartItems;

    public OrderDto getOrderDto(){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setOrderStatus(OrderStatus.Submitted);
        orderDto.setPrice(price);
        orderDto.setTotalAmount(totalAmount);
        orderDto.setPaymentType(paymentType);
        orderDto.setFullName(user.getFullName());
        orderDto.setDate(date);
        orderDto.setDescription(description);
        orderDto.setAddress(address);
        return orderDto;
    }
}
