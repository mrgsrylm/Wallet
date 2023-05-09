package com.poywallet.poywalletbackend.domain.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for Sign In request
 */
@Data
public class SignInRequest {
    @JsonProperty("username")
    @Size(min = 3, max = 20, message = "{username.size}")
    @NotBlank
    private String username;

    @JsonProperty("password")
    @Size(min = 6, max = 100, message = "{password.size}")
    @NotBlank
    private String password;
}
