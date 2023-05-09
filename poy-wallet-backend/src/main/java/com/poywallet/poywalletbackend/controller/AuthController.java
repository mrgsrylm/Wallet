package com.poywallet.poywalletbackend.controller;

import com.poywallet.poywalletbackend.domain.auth.SignInRequest;
import com.poywallet.poywalletbackend.domain.auth.SignUpRequest;
import com.poywallet.poywalletbackend.domain.auth.CommandResponse;
import com.poywallet.poywalletbackend.domain.auth.AuthResponse;
import com.poywallet.poywalletbackend.web.RestApiResponse;
import com.poywallet.poywalletbackend.domain.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;

import static com.poywallet.poywalletbackend.common.Constants.*;

@CrossOrigin(origins = CLIENT_BASE_URL)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final Clock clock;
    private final AuthService authService;

    /**
     * Registers users using their credentials and user info
     *
     * @param request
     * @return id of the registered user
     */
    @PostMapping("/sign-up")
    public ResponseEntity<RestApiResponse<CommandResponse>> signup(@Valid @RequestBody SignUpRequest request) {
        final CommandResponse response = authService.signUp(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Authenticates users by their credentials
     *
     * @param request
     * @return JwtResponse
     */
    @PostMapping("/sign-in")
    public ResponseEntity<RestApiResponse<AuthResponse>> login(@Valid @RequestBody SignInRequest request) {
        final AuthResponse response = authService.signIn(request);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }
}
