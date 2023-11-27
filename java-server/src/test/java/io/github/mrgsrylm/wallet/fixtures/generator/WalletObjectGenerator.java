package io.github.mrgsrylm.wallet.fixtures.generator;

import com.github.javafaker.Faker;
import io.github.mrgsrylm.wallet.fixtures.RandomUtil;
import io.github.mrgsrylm.wallet.model.User;
import io.github.mrgsrylm.wallet.model.Wallet;

import java.math.BigDecimal;

public class WalletObjectGenerator {

    private final Faker faker;

    public WalletObjectGenerator() {
        this.faker = new Faker();
    }

    public Wallet generateWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setId(RandomUtil.generateRandomLong(1, 100));
        wallet.setIban(generateIban());
        wallet.setName(generateName());
        wallet.setBalance(generateBalance());
        wallet.setUser(user);
//        wallet.setFromTransactions(generateTransactions(wallet, TransactionType.FROM));
//        wallet.setToTransactions(generateTransactions(wallet, TransactionType.TO));
        return wallet;
    }

    private String generateIban() {
        // You can implement your logic to generate IBAN or use a library if needed
        return faker.finance().iban();
    }

    private String generateName() {
        return faker.name().lastName() + "'s Wallet";
    }

    private BigDecimal generateBalance() {
        // You can customize the logic for generating a balance based on your requirements
        return BigDecimal.valueOf(faker.number().randomDouble(2, 100, 1000));
    }

//    private Set<Transaction> generateTransactions(Wallet wallet, TransactionType type) {
//        Set<Transaction> transactions = new HashSet<>();
//        int numberOfTransactions = faker.number().numberBetween(0, 5);
//
//        for (int i = 0; i < numberOfTransactions; i++) {
//            Transaction transaction = new Transaction();
//            transaction.setAmount(generateBalance());
//            transaction.setFromWallet(type == TransactionType.FROM ? wallet : null);
//            transaction.setToWallet(type == TransactionType.TO ? wallet : null);
//            // Set other transaction properties as needed
//            transactions.add(transaction);
//        }
//
//        return transactions;
//    }

}
