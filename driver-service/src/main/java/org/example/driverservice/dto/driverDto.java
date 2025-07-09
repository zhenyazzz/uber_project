package org.example.driverservice.dto;

public record driverDto(
         Long id,
         String name,
         String cartNumber,
         String currentLocation,
         boolean isAvailable
) {

}
