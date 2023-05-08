package com.poywallet.poywalletbackend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RefreshTokenRequest {
    @JsonProperty("refresh_token")
    @NotBlank
    private String refreshToken;

    @JsonProperty("username")
    @NotBlank
    private String username;
}
