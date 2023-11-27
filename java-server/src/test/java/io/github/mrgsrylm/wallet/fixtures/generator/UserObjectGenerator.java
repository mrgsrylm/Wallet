package io.github.mrgsrylm.wallet.fixtures.generator;

import com.github.javafaker.Faker;
import io.github.mrgsrylm.wallet.fixtures.RandomUtil;
import io.github.mrgsrylm.wallet.model.Role;
import io.github.mrgsrylm.wallet.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserObjectGenerator {

    private final Faker faker;

    public UserObjectGenerator() {
        this.faker = new Faker();
    }

    public User generateUser(Set<Role> roles) {
        User user = new User();
        user.setId(RandomUtil.generateRandomLong(1, 100));
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setUsername(generateUsername(user.getFirstName(), user.getLastName()));
        user.setEmail(generateEmail(user.getFirstName(), user.getLastName()));
        user.setPassword(generatePassword());
        user.setRoles(roles);
        // user.setWallets(generateWallets(user));
        return user;
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

//    private Set<Role> generateRoles() {
//        RoleObjectGenerator genRole = new RoleObjectGenerator();
//
//        // You can customize the logic for generating roles based on your requirements
//        Set<Role> roles = new HashSet<>();
//        roles.add(genRole.generateRole());
//        return roles;
//    }

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
