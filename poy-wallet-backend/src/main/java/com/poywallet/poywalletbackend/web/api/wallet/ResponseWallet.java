package com.poywallet.poywalletbackend.web.api.wallet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poywallet.poywalletbackend.web.api.user.ResponseUser;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Wallet response
 */
@Data
public class ResponseWallet {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("iban")
    private String iban;

    @JsonProperty("name")
    private String name;

    @JsonProperty("balance")
    private BigDecimal balance;

    @JsonProperty("user")
    private ResponseUser user;
}
