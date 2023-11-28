package io.github.mrgsrylm.wallet.fixtures.generator;

import com.github.javafaker.Faker;
import io.github.mrgsrylm.wallet.fixtures.RandomUtil;
import io.github.mrgsrylm.wallet.model.TransactionModel;
import io.github.mrgsrylm.wallet.model.TransactionTypeModel;
import io.github.mrgsrylm.wallet.model.WalletModel;
import io.github.mrgsrylm.wallet.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class TransactionObjectGenerator {

    private final Faker faker;

    public TransactionObjectGenerator() {
        this.faker = new Faker();
    }

    public TransactionModel generateTransaction(WalletModel fromWalletModel, WalletModel toWalletModel, TransactionTypeModel transactionTypeModel) {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setId(RandomUtil.generateRandomLong(1, 100));
        transactionModel.setReferenceNumber(UUID.randomUUID());
        transactionModel.setAmount(generateAmount());
        transactionModel.setDescription(generateDescription());
        transactionModel.setCreatedAt(generateCreatedAt());
        transactionModel.setStatus(generateStatus());
        transactionModel.setFromWallet(fromWalletModel);
        transactionModel.setToWallet(toWalletModel);
        transactionModel.setTransactionType(transactionTypeModel);
        return transactionModel;
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

