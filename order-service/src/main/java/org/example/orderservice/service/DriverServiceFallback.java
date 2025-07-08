package org.example.orderservice.service;

import org.example.orderservice.dto.Driver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class DriverServiceFallback implements DriverClientService {

    @Override
    public List<Driver> getNearbyDriver() {
        return new ArrayList<Driver>();
    }

    @Override
    public Driver updateDriverStatus(Long id, boolean status) {
        return new Driver(0L, "Fallback Driver", "AA000AA", "0,0", false);
    }
}
