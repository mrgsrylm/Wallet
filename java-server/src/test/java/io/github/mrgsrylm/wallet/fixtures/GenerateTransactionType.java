package io.github.mrgsrylm.wallet.fixtures;

import io.github.mrgsrylm.wallet.dto.transaction.TransactionTypeResponse;
import io.github.mrgsrylm.wallet.fixtures.generator.TransactionTypeObjectGenerator;
import io.github.mrgsrylm.wallet.model.TransactionType;

public class GenerateTransactionType {
    public static TransactionType build() {
        TransactionTypeObjectGenerator genTType = new TransactionTypeObjectGenerator();

        return genTType.generateTransactionType();
    }

    public static TransactionTypeResponse buildResponse() {
        TransactionType data = build();
        return TransactionTypeResponse.builder()
                .id(RandomUtil.generateRandomLong(1, 100))
                .name(data.getName())
                .description(data.getDescription())
                .build();
    }
}
