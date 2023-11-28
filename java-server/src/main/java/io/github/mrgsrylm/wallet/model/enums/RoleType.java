package io.github.mrgsrylm.wallet.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    user("user"),
    admin("admin");

    private String label;
}
