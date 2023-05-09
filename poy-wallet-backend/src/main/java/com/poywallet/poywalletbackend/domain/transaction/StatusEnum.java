package com.poywallet.poywalletbackend.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    PENDING("Pending"),
    SUCCESS("Success"),
    ERROR("Error");

    private final String label;
}
