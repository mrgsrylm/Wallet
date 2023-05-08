package com.poywallet.poywalletbackend.controller;

import com.poywallet.poywalletbackend.dto.request.SignInRequest;
import com.poywallet.poywalletbackend.dto.request.SignUpRequest;
import com.poywallet.poywalletbackend.dto.response.CommandResponse;
import com.poywallet.poywalletbackend.dto.response.SignInResponse;
import com.poywallet.poywalletbackend.dto.response.RestApiResponse;
import com.poywallet.poywalletbackend.service.AuthService;
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

import static com.poywallet.poywalletbackend.common.Constants.SUCCESS;

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
    public ResponseEntity<RestApiResponse<SignInResponse>> login(@Valid @RequestBody SignInRequest request) {
        final SignInResponse response = authService.signIn(request);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }
}
