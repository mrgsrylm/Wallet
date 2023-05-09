package com.poywallet.poywalletbackend.domain.transaction;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transaction_type")
@EqualsAndHashCode(of = {"name"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionType {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-transaction-type"
    )
    @SequenceGenerator(
            name = "sequence-transaction-type",
            sequenceName = "sequence_transaction_type",
            allocationSize = 5
    )
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 50)
    private String description;

    @OneToMany(mappedBy = "transactionType", cascade = CascadeType.ALL)
    private Set<Transaction> transactions = new HashSet<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setTransactionType(this);
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        transaction.setTransactionType(null);
    }
}
