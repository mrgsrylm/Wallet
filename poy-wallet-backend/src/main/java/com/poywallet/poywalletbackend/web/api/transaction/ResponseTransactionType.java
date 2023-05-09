package com.poywallet.poywalletbackend.web.api.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Data Transfer Object for Type response
 */
@Data
public class ResponseTransactionType {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
