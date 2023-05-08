package com.poywallet.poywalletbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for Sign In request
 */
@Data
public class SignInRequest {
    @Size(min = 3, max = 20, message = "{username.size}")
    @NotBlank
    private String username;

    @Size(min = 6, max = 100, message = "{password.size}")
    @NotBlank
    private String password;
}
