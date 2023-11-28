package io.github.mrgsrylm.wallet.model;

import io.github.mrgsrylm.wallet.model.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transactions")
@EqualsAndHashCode(of = {"referenceNumber"})
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID referenceNumber;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(length = 50)
    private String description;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_wallet_id", referencedColumnName = "id", nullable = false)
    private WalletModel fromWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_wallet_id", referencedColumnName = "id", nullable = false)
    private WalletModel toWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_type_id", referencedColumnName = "id", nullable = false)
    private TransactionTypeModel transactionType;
}
