package id.my.mrgsrylm.wallet.javaserver.dto.transaction;

import id.my.mrgsrylm.wallet.javaserver.dto.wallet.WalletResponse;
import id.my.mrgsrylm.wallet.javaserver.model.enums.TransactionStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Data Transfer Object for Transaction response
 */
@Data
@Builder
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
