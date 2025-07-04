package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.model.Order;
import org.example.orderservice.model.OrderStatus;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final DriverClientService driverClientService;

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.CREATED);
        Optional<Order> orderOptional = orderRepository.findById(order.getId());
        if (orderOptional.isPresent()) {

        }
    }
}
