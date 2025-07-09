package org.example.driverservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.driverservice.model.Driver;
import org.example.driverservice.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api.drivers")
@RequiredArgsConstructor
public class DriverController {
    @Autowired
    private final DriverService driverService;

    @GetMapping("/all")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/get{id}")
    public Optional<Driver> getDriverById(@PathVariable Long id) {
        return driverService.getDriverById(id);
    }

    @PostMapping("/crete")
    public Driver createDriver(@RequestBody Driver driver) {
        return driverService.createDriver(driver);
    }

    @PutMapping("/update")
    public Driver updateDriver(@RequestBody Driver driver) {return driverService.updateDriver(driver);}

    @DeleteMapping("/delete{id}")
    public void deleteDriver(@PathVariable Long id) {driverService.deleteDriverById(id); }

    @GetMapping("/nearby")
    public List<Driver> getNearbyDrivers() {
        return driverService.getNearbyDrivers();
    }

    @PutMapping("/{id}/status")
    public Driver updateDriverStatus(@PathVariable Long id, @RequestParam boolean status) {
        return driverService.updateDriverStatus(id, status);
    }





}
