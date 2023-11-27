package io.github.mrgsrylm.wallet.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"iban"})
@Entity
@Table(name = "wallet", indexes = {
        @Index(name = "wallet_user_id_iban_key", columnList = "user_id, iban", unique = true),
        @Index(name = "wallet_user_id_name_key", columnList = "user_id, name", unique = true)
})
public class Wallet {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-wallet"
    )
    @SequenceGenerator(
            name = "sequence-wallet",
            sequenceName = "sequence_wallet",
            allocationSize = 5
    )
    private Long id;

    @Column(length = 34, nullable = false, unique = true)
    private String iban;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "fromWallet", cascade = CascadeType.ALL)
    private Set<Transaction> fromTransactions = new HashSet<>();

    @OneToMany(mappedBy = "toWallet", cascade = CascadeType.ALL)
    private Set<Transaction> toTransactions = new HashSet<>();

    public void addFromTransaction(Transaction transaction) {
        fromTransactions.add(transaction);
        transaction.setFromWallet(this);
    }

    public void removeFromTransaction(Transaction transaction) {
        fromTransactions.remove(transaction);
        transaction.setFromWallet(null);
    }

    public void addToTransaction(Transaction transaction) {
        toTransactions.add(transaction);
        transaction.setToWallet(this);
    }

    public void removeToTransaction(Transaction transaction) {
        toTransactions.remove(transaction);
        transaction.setToWallet(null);
    }
}
