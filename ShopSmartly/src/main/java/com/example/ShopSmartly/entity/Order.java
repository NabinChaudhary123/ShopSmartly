package com.example.ShopSmartly.entity;

import com.example.ShopSmartly.dto.OrderDto;
import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItems> cartItems;

    public OrderDto getOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setDescription(description);
        orderDto.setAddress(address);
        orderDto.setPrice(price);
        orderDto.setTotalAmount(totalAmount);
        orderDto.setDate(date);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setFullName(user.getFullName());
        return orderDto;
    }
}
