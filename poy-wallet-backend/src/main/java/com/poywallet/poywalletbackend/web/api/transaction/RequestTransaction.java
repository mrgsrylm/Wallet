package com.poywallet.poywalletbackend.web.api.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poywallet.poywalletbackend.model.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object for Transaction request
 */
@Data
public class RequestTransaction {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("amount")
    @NotNull(message = "{amount.notnull}")
    private BigDecimal amount;

    @JsonProperty("description")
    @Size(max = 50, message = "{description.size}")
    private String description;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("reference_number")
    private UUID referenceNumber;

    @JsonProperty("status")
    private StatusEnum status;

    @JsonProperty("from_wallet_iban")
    @NotBlank(message = "fromWalletIban.notblank}")
    private String fromWalletIban;

    @JsonProperty("to_wallet_iban")
    @NotBlank(message = "{toWalletIban.notblank}")
    private String toWalletIban;

    @JsonProperty("transaction_type_id")
    @NotNull(message = "{transactionTypeId.notnull}")
    private Long transactionTypeId;
}
