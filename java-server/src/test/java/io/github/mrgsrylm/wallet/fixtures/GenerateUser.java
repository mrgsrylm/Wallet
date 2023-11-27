package io.github.mrgsrylm.wallet.fixtures;

import io.github.mrgsrylm.wallet.dto.auth.SignUpRequest;
import io.github.mrgsrylm.wallet.model.User;
import io.github.mrgsrylm.wallet.model.enums.RoleType;

import java.util.HashSet;
import java.util.Set;

public class GenerateUser {
    public static User build() {
        User user = new User();
        user.setId(1L);
        user.setFirstName(RandomUtil.generateRandomString());
        user.setLastName(RandomUtil.generateRandomString());
        user.setUsername(RandomUtil.generateRandomString());
        user.setEmail(RandomUtil.generateRandomString() + "test.com");
        user.setPassword(RandomUtil.generateRandomString());
        user.setRoles(GenerateRole.buildAsSet());
        return user;
    }

    public static SignUpRequest buildSignUpRequest() {
        Set<String> role = new HashSet<>();
        role.add("USER");

        return SignUpRequest.builder()
                .firstName(RandomUtil.generateRandomString())
                .lastName(RandomUtil.generateRandomString())
                .username(RandomUtil.generateRandomString())
                .email(RandomUtil.generateRandomString() + "test.com")
                .password(RandomUtil.generateRandomString())
                .roles(role).build();
    }
}
