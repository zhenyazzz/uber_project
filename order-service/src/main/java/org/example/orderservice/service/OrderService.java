package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.Driver;
import org.example.orderservice.model.Order;
import org.example.orderservice.model.OrderStatus;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final DriverClientService driverClientService;

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.CREATED);
        Order savedOrder = orderRepository.save(order);

        List<Driver> driverList = driverClientService.getNearbyDriver();
         if(!driverList.isEmpty()) {
             Driver driver = driverList.get(0);
             order.setDriverId(driver.getId());
             order.setStatus(OrderStatus.DRIVER_FOUND);
             orderRepository.save(order);
         }
         return savedOrder;
    }
}
