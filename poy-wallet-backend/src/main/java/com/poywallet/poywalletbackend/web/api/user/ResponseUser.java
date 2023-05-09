package com.poywallet.poywalletbackend.web.api.user;

import lombok.Data;

import java.util.Set;

/**
 * Data Transfer Object for User response
 */
@Data
public class ResponseUser {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String fullName;
    private Set<ResponseRole> roles;
}
