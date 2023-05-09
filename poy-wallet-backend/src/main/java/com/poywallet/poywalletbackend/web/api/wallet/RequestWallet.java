package com.poywallet.poywalletbackend.web.api.wallet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poywallet.poywalletbackend.validator.ValidIban;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Wallet request
 */
@Data
public class RequestWallet {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("iban")
    @ValidIban(message = "{iban.valid}")
    @NotBlank(message = "{iban.notblank}")
    private String iban;

    @JsonProperty("name")
    @Size(min = 3, max = 50, message = "{name.size}")
    @NotBlank(message = "{name.notblank}")
    private String name;

    @JsonProperty("balance")
    @NotNull(message = "{balance.notnull}")
    private BigDecimal balance;

    @JsonProperty("user_id")
    @NotNull(message = "{userId.notnull}")
    private Long userId;
}
