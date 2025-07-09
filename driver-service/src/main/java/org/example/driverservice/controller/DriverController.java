package org.example.driverservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.driverservice.model.Driver;
import org.example.driverservice.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @GetMapping("/nearby")
    public List<Driver> getNearbyDrivers() {
        return driverService.getNearbyDrivers();
    }

    @PutMapping("/{id}/status")
    public Driver updateDriverStatus(@PathVariable Long id, @RequestParam boolean status) {
        return driverService.updateDriverStatus(id, status);
    }
}
