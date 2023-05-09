package com.poywallet.poywalletbackend.web.api.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class RequestJwtRefresh {
    @JsonProperty("refresh_token")
    @NotBlank
    private String refreshToken;

    private String username;
}
