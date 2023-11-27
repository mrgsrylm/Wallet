package io.github.mrgsrylm.wallet.fixtures.generator;

import com.github.javafaker.Faker;
import io.github.mrgsrylm.wallet.fixtures.RandomUtil;
import io.github.mrgsrylm.wallet.model.Role;
import io.github.mrgsrylm.wallet.model.User;
import io.github.mrgsrylm.wallet.model.enums.RoleType;

import java.util.HashSet;
import java.util.Set;

public class RoleObjectGenerator {

    private final Faker faker;

    public RoleObjectGenerator() {
        this.faker = new Faker();
    }

    public Role generateRole() {
        Role role = new Role();
        role.setId(RandomUtil.generateRandomLong(1, 100));
        role.setType(generateRoleType());
//        role.setUsers(generateUsers(role));
        return role;
    }

    private RoleType generateRoleType() {
        return faker.options().option(RoleType.class);
    }

    private Set<User> generateUsers(Role role) {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        // You can customize the logic for generating users based on your requirements
        UserObjectGenerator userObjectGenerator = new UserObjectGenerator();
        Set<User> users = new HashSet<>();
        for (int i = 0; i < faker.number().numberBetween(1, 5); i++) {
            User user = userObjectGenerator.generateUser(roleSet);
            user.getRoles().add(role);
            users.add(user);
        }
        return users;
    }
}