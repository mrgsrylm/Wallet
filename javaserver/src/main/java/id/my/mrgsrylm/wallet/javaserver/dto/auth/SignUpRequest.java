package id.my.mrgsrylm.wallet.javaserver.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Data Transfer Object for Signup request
 */
@Data
@Builder
@AllArgsConstructor
public class SignUpRequest {
    @Size(min = 3, max = 50, message = "{firstName.size}")
    @NotBlank(message = "{firstName.notblank}")
    private String firstName;

    @Size(min = 3, max = 50, message = "{lastName.size}")
    @NotBlank(message = "{lastName.notblank}")
    private String lastName;

    @Size(min = 3, max = 20, message = "{username.size}")
    @NotBlank(message = "{username.notblank}")
    private String username;

    @Email(message = "email.acceptable")
    @Size(min = 6, max = 50, message = "{email.size}")
    @NotBlank(message = "email.notblank}")
    private String email;

    @Size(min = 6, max = 100, message = "{password.size}")
    @NotBlank(message = "password.notblank}")
    private String password;
}
