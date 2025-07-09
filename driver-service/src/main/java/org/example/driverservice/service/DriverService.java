package org.example.driverservice.service;

import lombok.RequiredArgsConstructor;
import org.example.driverservice.model.Driver;
import org.example.driverservice.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;

    public List<Driver> getAllDrivers() {return driverRepository.findAll();}

    public Optional<Driver> getDriverById(Long id) {return driverRepository.findById(id);}

    public Driver createDriver(Driver driver) {return driverRepository.save(driver);}

    public Driver updateDriver(Driver driver) {return driverRepository.save(driver);}

    public void deleteDriver(Driver driver) {driverRepository.delete(driver);}

    public void deleteDriverById(Long id) {driverRepository.deleteById(id);}



    public List<Driver> getNearbyDrivers() {
        return driverRepository.findAll();
    }

    public Driver updateDriverStatus(Long id,boolean status) {
        Optional<Driver> driver = driverRepository.findById(id);
        if (driver.isPresent()) {
            driver.get().setAvailable(status);
        }
        return driverRepository.save(driver.get());
    }

}
