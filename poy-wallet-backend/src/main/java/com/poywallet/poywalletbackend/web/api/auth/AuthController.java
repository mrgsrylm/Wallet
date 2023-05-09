package com.poywallet.poywalletbackend.web.api.auth;

import com.poywallet.poywalletbackend.model.User;
import com.poywallet.poywalletbackend.security.UserDetailsImpl;
import com.poywallet.poywalletbackend.service.UserService;
import com.poywallet.poywalletbackend.web.ResponseCommand;
import com.poywallet.poywalletbackend.web.ResponseMessage;
import com.poywallet.poywalletbackend.web.RestApiResponse;
import com.poywallet.poywalletbackend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<RestApiResponse<ResponseCommand>> signup(@Valid @RequestBody RequestSignUp request) {
        final ResponseCommand response = authService.signUp(request);
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
    public ResponseEntity<RestApiResponse<ResponseAuth>> login(@Valid @RequestBody RequestSignIn request) {
        final ResponseAuth response = authService.signIn(request);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }
}
