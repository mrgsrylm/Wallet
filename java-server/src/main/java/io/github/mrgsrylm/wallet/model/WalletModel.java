package io.github.mrgsrylm.wallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "wallets")
@EqualsAndHashCode(of = {"iban"})
public class WalletModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 34, nullable = false, unique = true)
    private String iban;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserModel user;

    @OneToMany(mappedBy = "fromWallet", cascade = CascadeType.ALL)
    private Set<TransactionModel> fromTransactions = new HashSet<>();

    @OneToMany(mappedBy = "toWallet", cascade = CascadeType.ALL)
    private Set<TransactionModel> toTransactions = new HashSet<>();

    public void addFromTransaction(TransactionModel transactionModel) {
        fromTransactions.add(transactionModel);
        transactionModel.setFromWallet(this);
    }

    public void removeFromTransaction(TransactionModel transactionModel) {
        fromTransactions.remove(transactionModel);
        transactionModel.setFromWallet(null);
    }

    public void addToTransaction(TransactionModel transactionModel) {
        toTransactions.add(transactionModel);
        transactionModel.setToWallet(this);
    }

    public void removeToTransaction(TransactionModel transactionModel) {
        toTransactions.remove(transactionModel);
        transactionModel.setToWallet(null);
    }
}
