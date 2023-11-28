package io.github.mrgsrylm.wallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transaction_types")
@EqualsAndHashCode(of = {"name"})
public class TransactionTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 50)
    private String description;

    @OneToMany(mappedBy = "transactionType", cascade = CascadeType.ALL)
    private Set<TransactionModel> transactions = new HashSet<>();

    public void addTransaction(TransactionModel transactionModel) {
        transactions.add(transactionModel);
        transactionModel.setTransactionType(this);
    }

    public void removeTransaction(TransactionModel transactionModel) {
        transactions.remove(transactionModel);
        transactionModel.setTransactionType(null);
    }
}
