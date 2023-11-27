package io.github.mrgsrylm.wallet.fixtures.generator;

import com.github.javafaker.Faker;
import io.github.mrgsrylm.wallet.fixtures.RandomUtil;
import io.github.mrgsrylm.wallet.model.Transaction;
import io.github.mrgsrylm.wallet.model.TransactionType;
import io.github.mrgsrylm.wallet.model.Wallet;
import io.github.mrgsrylm.wallet.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class TransactionObjectGenerator {

    private final Faker faker;

    public TransactionObjectGenerator() {
        this.faker = new Faker();
    }

    public Transaction generateTransaction(Wallet fromWallet, Wallet toWallet, TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setId(RandomUtil.generateRandomLong(1, 100));
        transaction.setReferenceNumber(UUID.randomUUID());
        transaction.setAmount(generateAmount());
        transaction.setDescription(generateDescription());
        transaction.setCreatedAt(generateCreatedAt());
        transaction.setStatus(generateStatus());
        transaction.setFromWallet(fromWallet);
        transaction.setToWallet(toWallet);
        transaction.setTransactionType(transactionType);
        return transaction;
    }

    private BigDecimal generateAmount() {
        // You can customize the logic for generating the amount based on your requirements
        return BigDecimal.valueOf(faker.number().randomDouble(2, 10, 100));
    }

    private String generateDescription() {
        return faker.lorem().sentence();
    }

    private Instant generateCreatedAt() {
        // You can customize the logic for generating the creation time based on your requirements
        return Instant.now();
    }

    private TransactionStatus generateStatus() {
        return faker.options().option(TransactionStatus.class);
    }
}

