package id.my.mrgsrylm.wallet.javaserver.fixtures;

import id.my.mrgsrylm.wallet.javaserver.dto.transaction.TransactionTypeResponse;
import id.my.mrgsrylm.wallet.javaserver.fixtures.generator.TransactionTypeObjectGenerator;
import id.my.mrgsrylm.wallet.javaserver.model.TransactionTypeModel;

public class GenerateTransactionType {
    public static TransactionTypeModel build() {
        TransactionTypeObjectGenerator genTType = new TransactionTypeObjectGenerator();

        return genTType.generateTransactionType();
    }

    public static TransactionTypeResponse buildResponse() {
        TransactionTypeModel data = build();
        return TransactionTypeResponse.builder()
                .id(RandomUtil.generateRandomLong(1, 100))
                .name(data.getName())
                .description(data.getDescription())
                .build();
    }
}
