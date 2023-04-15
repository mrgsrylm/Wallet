package com.pollspolling.backend.delivery.controller;

import com.pollspolling.backend.delivery.payload.request.LoginRequest;
import com.pollspolling.backend.delivery.payload.request.RegistrationRequest;
import com.pollspolling.backend.delivery.payload.response.JwtAuthResponse;
import com.pollspolling.backend.delivery.result.CustomApiResponse;
import com.pollspolling.backend.domain.entity.User;
import com.pollspolling.backend.repository.RoleRepository;
import com.pollspolling.backend.repository.UserRepository;
import com.pollspolling.backend.security.JwtTokenProvider;
import com.pollspolling.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/v1/")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtAuthResponse res = authService.authenticationUser(loginRequest);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        User user = authService.registerUser(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity.created(location).body(new CustomApiResponse(true, "User registered successfully"));
    }
}
