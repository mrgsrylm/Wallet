package id.my.mrgsrylm.wallet.javaserver.dto.transaction;

import id.my.mrgsrylm.wallet.javaserver.model.enums.TransactionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object for Transaction request
 */
@Data
@Builder
public class TransactionRequest {

    private Long id;

    @NotNull(message = "{amount.notnull}")
    private BigDecimal amount;

    @Size(max = 50, message = "{description.size}")
    private String description;

    private Instant createdAt;

    private UUID referenceNumber;

    private TransactionStatus status;

    @NotBlank(message = "fromWalletIban.notblank}")
    private String fromWalletIban;

    @NotBlank(message = "{toWalletIban.notblank}")
    private String toWalletIban;

    @NotNull(message = "{typeId.notnull}")
    private Long transactionTypeId;
}

