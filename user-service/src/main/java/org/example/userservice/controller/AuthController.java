package org.example.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.CreateUserRequest;
import org.example.userservice.dto.LoginRequest;
import org.example.userservice.dto.LoginResponse;
import org.example.userservice.dto.SingUpRequest;
import org.example.userservice.jwt.JwtUtils;
import org.example.userservice.model.Role;
import org.example.userservice.model.RoleType;
import org.example.userservice.model.User;
import org.example.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/singUp")
    public ResponseEntity<?> singUp(@RequestBody SingUpRequest singUpRequest) {
        if (userService.existsByUsername(singUpRequest.userName())) {
            return ResponseEntity.badRequest().body(Map.of("message", "username already exist"));
        }
        userService.createUser(new CreateUserRequest(passwordEncoder.encode(singUpRequest.password()
        ), singUpRequest.userName(), "", Set.of(RoleType.ROLE_USER.toString())));
        return ResponseEntity.ok(Map.of("message", "success"));
    }

    @PostMapping("/singIn")
    public ResponseEntity<?> singIn(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtils.generateTokenFromUsername(userDetails);
            return ResponseEntity.ok(new LoginResponse(
                    jwt,
                    userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
                    userDetails.getUsername()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        String token = jwtUtils.getJwtFromHeader(request);
        if (token == null || jwtUtils.validateJwtToken(token)) {
            return ResponseEntity.ok(Map.of("valid", false, "token", token));

        }
        if (userService.existsByUsername(jwtUtils.getUserNameFromJwtToken(token))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "username already exist"));
        }
        return ResponseEntity.ok(Map.of(
                "valid", true,
                "token", token,
                "username", jwtUtils.getUserNameFromJwtToken(token)
        ));
    }



}
