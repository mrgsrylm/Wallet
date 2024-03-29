package id.my.mrgsrylm.wallet.javaserver.fixtures.generator;

import com.github.javafaker.Faker;
import id.my.mrgsrylm.wallet.javaserver.fixtures.RandomUtil;
import id.my.mrgsrylm.wallet.javaserver.model.UserModel;
import id.my.mrgsrylm.wallet.javaserver.model.enums.RoleType;

import java.util.Set;

public class UserObjectGenerator {

    private final Faker faker;

    public UserObjectGenerator() {
        this.faker = new Faker();
    }

    public UserModel generateUser(RoleType roleType) {
        UserModel userModel = new UserModel();
        userModel.setId(RandomUtil.generateRandomLong(1, 100));
        userModel.setFirstName(faker.name().firstName());
        userModel.setLastName(faker.name().lastName());
        userModel.setUsername(generateUsername(userModel.getFirstName(), userModel.getLastName()));
        userModel.setEmail(generateEmail(userModel.getFirstName(), userModel.getLastName()));
        userModel.setPassword(generatePassword());
        userModel.setRole(roleType);
        // user.setWallets(generateWallets(user));
        return userModel;
    }

    private String generateUsername(String firstName, String lastName) {
        return (firstName.substring(0, 1) + lastName).toLowerCase() + faker.number().randomNumber();
    }

    private String generateEmail(String firstName, String lastName) {
        return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
    }

    private String generatePassword() {
        // You can customize the logic for generating passwords based on your requirements
        return faker.internet().password();
    }

//    private Set<Wallet> generateWallets(User user) {
//        // You can customize the logic for generating wallets based on your requirements
//        WalletObjectGenerator walletObjectGenerator = new WalletObjectGenerator();
//        Set<Wallet> wallets = new HashSet<>();
//        for (int i = 0; i < faker.number().numberBetween(1, 3); i++) {
//            wallets.add(walletObjectGenerator.generateWallet(user));
//        }
//        return wallets;
//    }
}
