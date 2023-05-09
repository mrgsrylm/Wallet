package com.poywallet.poywalletbackend.web.api.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class ResponseJwtRefresh {
    @JsonProperty("token")
    @NotBlank
    private String token;
    @JsonProperty("refresh_token")
    @NotBlank
    private String refreshToken;
}
