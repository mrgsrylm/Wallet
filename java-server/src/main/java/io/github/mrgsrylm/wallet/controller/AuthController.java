package io.github.mrgsrylm.wallet.controller;

import io.github.mrgsrylm.wallet.dto.ApiResponse;
import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.auth.JwtResponse;
import io.github.mrgsrylm.wallet.dto.auth.LoginRequest;
import io.github.mrgsrylm.wallet.dto.auth.SignUpRequest;
import io.github.mrgsrylm.wallet.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Instant;

import static io.github.mrgsrylm.wallet.common.Constants.SUCCESS;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final Clock clock;

    /**
     * Registers users using their credentials and user info
     *
     * @param request
     * @return id of the registered user
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<CommandResponse>> signup(@Valid @RequestBody SignUpRequest request) {
        final CommandResponse response = authService.signup(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Authenticates users by their credentials
     *
     * @param request
     * @return JwtResponse
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginRequest request) {
        final JwtResponse response = authService.login(request);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }
}
