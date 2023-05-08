package com.poywallet.poywalletbackend.dto.response;

import com.poywallet.poywalletbackend.model.StatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Data Transfer Object for Transaction response
 */
@Data
public class TransactionResponse {

    private Long id;
    private BigDecimal amount;
    private String description;
    private String createdAt;
    private UUID referenceNumber;
    private StatusEnum status;
    private WalletResponse fromWallet;
    private WalletResponse toWallet;
    private TransactionTypeResponse transactionTypeResponse;
}

