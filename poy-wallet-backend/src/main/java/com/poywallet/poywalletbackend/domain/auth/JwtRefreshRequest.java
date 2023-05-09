package com.poywallet.poywalletbackend.domain.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class JwtRefreshRequest {
    @JsonProperty("refresh_token")
    @NotBlank
    private String refreshToken;

    @JsonProperty("username")
    @NotBlank
    private String username;
}
