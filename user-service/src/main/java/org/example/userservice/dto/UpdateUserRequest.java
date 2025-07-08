package org.example.userservice.dto;

import java.util.Set;

public record UpdateUserRequest(
        String userName,
        String password,
        String email,
        Set<String> roles
) {
}
