package id.my.mrgsrylm.wallet.javaserver.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing standard JWT claims.
 */
@Getter
@RequiredArgsConstructor
public enum TokenClaims {
    ID("id"),
    USERNAME("username"),
    ROLES("roles"),
    EXPIRES_AT("exp"),

    JWT_ID("jti"),
    TYPE("typ"),
    SUBJECT("sub"),
    ISSUED_AT("iat");

    private final String value;
}