package io.github.mrgsrylm.wallet.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private String label;
}
