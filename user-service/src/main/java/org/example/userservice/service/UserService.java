package org.example.userservice.service;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.CreateUserRequest;
import org.example.userservice.dto.UpdateUserRequest;
import org.example.userservice.dto.UserDto;
import org.example.userservice.model.Role;
import org.example.userservice.model.RoleType;
import org.example.userservice.model.User;
import org.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto createUser(CreateUserRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (userRepository.existsByUsername(request.userName())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setUsername(request.userName());
        user.setPassword(request.password());
        user.setEmail(request.email());
        user.setRoles(request.roles().stream()
                .map(roleName -> {
                    RoleType roleType = "ROLE_ADMIN".equals(roleName)
                            ? RoleType.ROLE_ADMIN
                            : RoleType.ROLE_USER;
                    return new Role(roleType);
                })
                .collect(Collectors.toSet()));
        return toDto(userRepository.save(user));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    public UserDto updateUser(Long id, UpdateUserRequest userDetails) {
        User user = getUserById(id);

        if (userDetails.userName() != null && !userDetails.userName().equals(user.getUsername())) {
            if (userRepository.existsByUsername(userDetails.userName())) {
                throw new RuntimeException("Username already exists");
            }
            user.setUsername(userDetails.userName());
        }

        if (userDetails.email() != null && !userDetails.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userDetails.email())) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(userDetails.email());
        }

        if (userDetails.password() != null) {
            user.setPassword(userDetails.password());
        }

        if (userDetails.roles() != null) {
            Set<Role> roles = userDetails.roles().stream()
                    .map(roleName -> {
                        RoleType roleType = "ROLE_ADMIN".equals(roleName)
                                ? RoleType.ROLE_ADMIN
                                : RoleType.ROLE_USER;
                        return new Role(roleType);
                    })
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return toDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet())
        );
    }

    public UserDto findUserById(Long id) {
        return toDto(Objects.requireNonNull(userRepository.findById(id).orElse(null)));
    }
}
