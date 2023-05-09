package com.poywallet.poywalletbackend.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Data Transfer Object for Role response
 */
@Data
public class RoleResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("type")
    private RoleEnum type;
}
