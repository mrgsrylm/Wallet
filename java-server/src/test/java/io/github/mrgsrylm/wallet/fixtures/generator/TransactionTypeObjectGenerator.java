package io.github.mrgsrylm.wallet.fixtures.generator;

import com.github.javafaker.Faker;
import io.github.mrgsrylm.wallet.fixtures.RandomUtil;
import io.github.mrgsrylm.wallet.model.TransactionType;

public class TransactionTypeObjectGenerator {
    private final Faker faker;

    public TransactionTypeObjectGenerator() {
        this.faker = new Faker();
    }

    public TransactionType generateTransactionType() {
        TransactionType transactionType = new TransactionType();
        transactionType.setId(RandomUtil.generateRandomLong(1, 100));
        transactionType.setName(generateName());
        transactionType.setDescription(generateDescription());
        // transactionType.setTransactions(generateTransactions(transactionType));
        return transactionType;
    }

    private String generateName() {
        return faker.lorem().word();
    }

    private String generateDescription() {
        return faker.lorem().sentence();
    }

//    private Set<Transaction> generateTransactions(TransactionType transactionType) {
//        // You can customize the logic for generating transactions based on your requirements
//        TransactionObjectGenerator transactionObjectGenerator = new TransactionObjectGenerator();
//        Set<Transaction> transactions = new HashSet<>();
//        for (int i = 0; i < faker.number().numberBetween(1, 5); i++) {
//            Transaction transaction = transactionObjectGenerator.generateTransaction();
//            transaction.setTransactionType(transactionType);
//            transactions.add(transaction);
//        }
//        return transactions;
//    }
}
