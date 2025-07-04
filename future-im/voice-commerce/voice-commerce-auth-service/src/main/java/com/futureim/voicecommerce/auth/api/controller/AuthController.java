package com.futureim.voicecommerce.auth.api.controller;

import com.futureim.voicecommerce.auth.api.dto.AuthRequest;
import com.futureim.voicecommerce.auth.api.dto.AuthResponse;
import com.futureim.voicecommerce.auth.api.dto.CreateUserRequest;
import com.futureim.voicecommerce.auth.api.dto.UserDTO;
import com.futureim.voicecommerce.auth.domain.model.User;
import com.futureim.voicecommerce.auth.domain.service.AuthenticationService;
import com.futureim.voicecommerce.auth.domain.service.JwtService;
import com.futureim.voicecommerce.auth.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        String accessToken = authenticationService.authenticate(request.getUsername(), request.getPassword());
        String refreshToken = jwtService.generateRefreshToken(authenticationService.getCurrentUser());
        
        return ResponseEntity.ok(AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType("Bearer")
            .expiresIn(3600) // 1 hour
            .user(UserDTO.fromUser(authenticationService.getCurrentUser()))
            .build());
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody CreateUserRequest request) {
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPhoneNumber(request.getPhoneNumber());

            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(UserDTO.fromUser(createdUser));
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            throw e;
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }
        
        String newAccessToken = authenticationService.refreshToken(refreshToken);
        User user = authenticationService.getCurrentUser();
        
        return ResponseEntity.ok(AuthResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(refreshToken) // Keep the same refresh token
            .tokenType("Bearer")
            .expiresIn(3600) // 1 hour
            .user(UserDTO.fromUser(user))
            .build());
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return ResponseEntity.ok(UserDTO.fromUser(authenticationService.getCurrentUser()));
    }
}
