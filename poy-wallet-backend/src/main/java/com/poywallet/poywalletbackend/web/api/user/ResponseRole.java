package com.poywallet.poywalletbackend.web.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poywallet.poywalletbackend.model.RoleEnum;
import lombok.Data;

/**
 * Data Transfer Object for Role response
 */
@Data
public class ResponseRole {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("type")
    private RoleEnum type;
}
