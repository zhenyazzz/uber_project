package org.example.orderservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Driver;

@FeignClient(name = "driver-service")
public interface DriverClientService {
    @GetMapping("/api/drivers/{id}")
    Driver getDriverById(@PathVariable("id") Long id);


}
