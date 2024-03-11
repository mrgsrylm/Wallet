package id.my.mrgsrylm.wallet.javaserver.fixtures.generator;

import com.github.javafaker.Faker;
import id.my.mrgsrylm.wallet.javaserver.fixtures.RandomUtil;
import id.my.mrgsrylm.wallet.javaserver.model.TransactionTypeModel;

public class TransactionTypeObjectGenerator {
    private final Faker faker;

    public TransactionTypeObjectGenerator() {
        this.faker = new Faker();
    }

    public TransactionTypeModel generateTransactionType() {
        TransactionTypeModel transactionTypeModel = new TransactionTypeModel();
        transactionTypeModel.setId(RandomUtil.generateRandomLong(1, 100));
        transactionTypeModel.setName(generateName());
        transactionTypeModel.setDescription(generateDescription());
        // transactionType.setTransactions(generateTransactions(transactionType));
        return transactionTypeModel;
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
