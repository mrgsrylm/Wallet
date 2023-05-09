package com.poywallet.poywalletbackend.domain.wallet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poywallet.poywalletbackend.domain.user.UserResponse;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Wallet response
 */
@Data
public class WalletResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("iban")
    private String iban;

    @JsonProperty("name")
    private String name;

    @JsonProperty("balance")
    private BigDecimal balance;

    @JsonProperty("user")
    private UserResponse user;
}
