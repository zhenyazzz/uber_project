package org.example.driverservice.mapper;

import org.example.driverservice.dto.driverDto;
import org.example.driverservice.model.Driver;

public class driverMapper {

    static public driverDto toDTO(Driver driver) {
        return new driverDto(
                driver.getId(),
                driver.getName(),
                driver.getCartNumber(),
                driver.getCurrentLocation(),
                driver.isAvailable()
        );
    }

    static public Driver toEntity(driverDto DTO){
        return new Driver(
                DTO.id(),
                DTO.name(),
                DTO.cartNumber(),
                DTO.currentLocation(),
                DTO.isAvailable()
        );
    }

}
