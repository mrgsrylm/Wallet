package io.github.mrgsrylm.wallet.dto.user;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Data Transfer Object for User response
 */
@Data
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String fullName;
    private Set<RoleResponse> roles;
}

