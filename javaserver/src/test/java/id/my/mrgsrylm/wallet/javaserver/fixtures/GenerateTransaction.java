package id.my.mrgsrylm.wallet.javaserver.fixtures;

import id.my.mrgsrylm.wallet.javaserver.dto.transaction.TransactionRequest;
import id.my.mrgsrylm.wallet.javaserver.dto.transaction.TransactionResponse;
import id.my.mrgsrylm.wallet.javaserver.fixtures.generator.TransactionObjectGenerator;
import id.my.mrgsrylm.wallet.javaserver.model.TransactionModel;
import id.my.mrgsrylm.wallet.javaserver.model.TransactionTypeModel;
import id.my.mrgsrylm.wallet.javaserver.model.WalletModel;

import java.util.ArrayList;
import java.util.List;

public class GenerateTransaction {
    public static TransactionModel build() {
        WalletModel walletModel1 = GenerateWallet.build();
        WalletModel walletModel2 = GenerateWallet.build();
        TransactionTypeModel type = GenerateTransactionType.build();
        TransactionObjectGenerator generator = new TransactionObjectGenerator();

        return generator.generateTransaction(walletModel1, walletModel2, type);
    }

    public static TransactionRequest buildTransactionRequest() {
        TransactionModel transactionModel = build();

        return TransactionRequest.builder()
                .amount(transactionModel.getAmount())
                .description(transactionModel.getDescription())
                .createdAt(transactionModel.getCreatedAt())
                .referenceNumber(transactionModel.getReferenceNumber())
                .status(transactionModel.getStatus())
                .fromWalletIban(transactionModel.getFromWallet().getIban())
                .toWalletIban(transactionModel.getToWallet().getIban())
                .transactionTypeId(transactionModel.getTransactionType().getId())
                .build();
    }

    public static List<TransactionModel> buildList() {
        TransactionModel transactionModel1 = build();
        TransactionModel transactionModel2 = build();
        List<TransactionModel> transactionModels = new ArrayList<>();
        transactionModels.add(transactionModel1);
        transactionModels.add(transactionModel2);

        return transactionModels;
    }

    public static TransactionResponse buildTransactionResponse() {
        TransactionModel data = build();
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
        TransactionModel data = build();

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
