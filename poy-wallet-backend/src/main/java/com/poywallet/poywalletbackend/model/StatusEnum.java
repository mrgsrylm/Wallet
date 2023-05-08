package com.poywallet.poywalletbackend.model;

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
