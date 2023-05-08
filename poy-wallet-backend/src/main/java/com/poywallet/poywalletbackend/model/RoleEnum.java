package com.poywallet.poywalletbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ROLE_USER("User"),
    ROLE_ADMIN("Admin");

    private final String label;
}
