package com.poywallet.poywalletbackend.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

/**
 * Data Transfer Object for Sign Up request
 */
@Data
public class SignUpRequest {
    private Long id;

    @JsonProperty("first_name")
    @Size(min = 3, max = 50, message = "{firstName.size}")
    @NotBlank(message = "{firstName.notblank}")
    private String firstName;

    @JsonProperty("last_name")
    @Size(min = 3, max = 50, message = "{lastName.size}")
    @NotBlank(message = "{lastName.notblank}")
    private String lastName;

    @JsonProperty("username")
    @Size(min = 3, max = 20, message = "{username.size}")
    @NotBlank(message = "{username.notblank}")
    private String username;

    @JsonProperty("email")
    @Email(message = "email.acceptable")
    @Size(min = 6, max = 50, message = "{email.size}")
    @NotBlank(message = "email.notblank}")
    private String email;

    @JsonProperty("password")
    @Size(min = 6, max = 100, message = "{password.size}")
    @NotBlank(message = "password.notblank}")
    private String password;

    private Set<String> roles;
}
