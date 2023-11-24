package io.github.mrgsrylm.wallet.dto.transaction;

import io.github.mrgsrylm.wallet.dto.wallet.WalletResponse;
import io.github.mrgsrylm.wallet.model.enums.TransactionStatus;
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
    private TransactionStatus status;
    private WalletResponse fromWallet;
    private WalletResponse toWallet;
    private TransactionTypeResponse type;
}
