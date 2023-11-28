package io.github.mrgsrylm.wallet.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER");

    private String label;
}
