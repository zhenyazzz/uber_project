package org.example.userservice.dto;

import java.util.Set;

public record UserDto(
        Long id,
        String userName,
        String email,
        String password,
        Set<String> roles
) {
}
