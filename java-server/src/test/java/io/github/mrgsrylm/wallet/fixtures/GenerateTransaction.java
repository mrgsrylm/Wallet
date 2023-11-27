package io.github.mrgsrylm.wallet.fixtures;

import io.github.mrgsrylm.wallet.dto.transaction.TransactionRequest;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionResponse;
import io.github.mrgsrylm.wallet.fixtures.generator.TransactionObjectGenerator;
import io.github.mrgsrylm.wallet.model.Transaction;
import io.github.mrgsrylm.wallet.model.TransactionType;
import io.github.mrgsrylm.wallet.model.Wallet;

import java.util.ArrayList;
import java.util.List;

public class GenerateTransaction {
    public static Transaction build() {
        Wallet wallet1 = GenerateWallet.build();
        Wallet wallet2 = GenerateWallet.build();
        TransactionType type = GenerateTransactionType.build();
        TransactionObjectGenerator generator = new TransactionObjectGenerator();

        return generator.generateTransaction(wallet1, wallet2, type);
    }

    public static TransactionRequest buildTransactionRequest() {
        Transaction transaction = build();

        return TransactionRequest.builder()
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .referenceNumber(transaction.getReferenceNumber())
                .status(transaction.getStatus())
                .fromWalletIban(transaction.getFromWallet().getIban())
                .toWalletIban(transaction.getToWallet().getIban())
                .transactionTypeId(transaction.getTransactionType().getId())
                .build();
    }

    public static List<Transaction> buildList() {
        Transaction transaction1 = build();
        Transaction transaction2 = build();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        return transactions;
    }

    public static TransactionResponse buildTransactionResponse() {
        Transaction data = build();
        return TransactionResponse.builder()
                .id(RandomUtil.generateRandomLong(1, 100))
                .amount(data.getAmount())
                .description(data.getDescription())
                .createdAt(String.valueOf(data.getCreatedAt()))
                .referenceNumber(data.getReferenceNumber())
                .fromWallet(GenerateWallet.buildWalletResponse())
                .toWallet(GenerateWallet.buildWalletResponse())
                .type(GenerateTransactionType.buildResponse())
                .build();
    }

    public static List<TransactionResponse> buildListTransactionResponse() {
        TransactionResponse transactionResponse1 = buildTransactionResponse();
        TransactionResponse transactionResponse2 = buildTransactionResponse();

        List<TransactionResponse> data = new ArrayList<>();
        data.add(transactionResponse1);
        data.add(transactionResponse2);

        return data;
    }

    public static TransactionRequest bulidTransactionRequest() {
        Transaction data = build();

        return TransactionRequest.builder()
                .amount(data.getAmount())
                .description(data.getDescription())
                .createdAt(data.getCreatedAt())
                .referenceNumber(data.getReferenceNumber())
                .status(data.getStatus())
                .fromWalletIban(data.getFromWallet().getIban())
                .toWalletIban(data.getToWallet().getIban())
                .transactionTypeId(data.getTransactionType().getId())
                .build();
    }
}
