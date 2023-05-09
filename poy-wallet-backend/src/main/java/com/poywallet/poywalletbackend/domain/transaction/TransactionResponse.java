package com.poywallet.poywalletbackend.domain.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poywallet.poywalletbackend.domain.wallet.WalletResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Data Transfer Object for Transaction response
 */
@Data
public class TransactionResponse {
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
    private WalletResponse fromWallet;

    @JsonProperty("to_wallet")
    private WalletResponse toWallet;

    @JsonProperty("transaction_type")
    private TransactionTypeResponse transactionTypeResponse;
}

