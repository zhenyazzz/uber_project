package org.example.orderservice.service;

import org.example.orderservice.dto.Driver;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "driver-service")
public interface DriverClientService {
    @GetMapping("/api/drivers/nearby")
    List<Driver> getNearbyDriver();

    @PostMapping("/api/{id}/status")
    Driver updateDriverStatus(@PathVariable("id") Long id ,@RequestParam boolean status);
}
