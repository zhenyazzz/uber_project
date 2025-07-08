package org.example.orderservice.dto;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class Driver {
    private Long id;
    private String name;
    private String cartNumber;
    private String currentLocation;
    private boolean isAvailable;
}
