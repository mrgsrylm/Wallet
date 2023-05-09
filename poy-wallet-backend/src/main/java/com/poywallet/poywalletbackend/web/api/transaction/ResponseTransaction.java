package com.poywallet.poywalletbackend.web.api.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poywallet.poywalletbackend.web.api.wallet.ResponseWallet;
import com.poywallet.poywalletbackend.model.StatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Data Transfer Object for Transaction response
 */
@Data
public class ResponseTransaction {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("description")
    private String description;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("reference_number")
    private UUID referenceNumber;

    @JsonProperty("status")
    private StatusEnum status;

    @JsonProperty("from_wallet")
    private ResponseWallet fromWallet;

    @JsonProperty("to_wallet")
    private ResponseWallet toWallet;

    @JsonProperty("transaction_type")
    private ResponseTransactionType responseTransactionType;
}

