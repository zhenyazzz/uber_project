package org.example.orderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String userId;
    private String pickupLocation;
    private String deliveryLocation;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Long DriverId;
}
