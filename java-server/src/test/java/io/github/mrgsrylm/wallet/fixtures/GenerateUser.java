package io.github.mrgsrylm.wallet.fixtures;

import io.github.mrgsrylm.wallet.dto.auth.SignUpRequest;
import io.github.mrgsrylm.wallet.dto.user.UserResponse;
import io.github.mrgsrylm.wallet.fixtures.generator.UserObjectGenerator;
import io.github.mrgsrylm.wallet.model.User;

public class GenerateUser {
    public static User build() {
        UserObjectGenerator generator = new UserObjectGenerator();

        return generator.generateUser(GenerateRole.buildAsSet());
    }

    public static SignUpRequest buildSignUpRequest() {
        User user = build();

        return SignUpRequest.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(GenerateRole.buildAsSetStringType()).build();
    }

    public static UserResponse buildUserResponse() {
        User data = build();
        return UserResponse.builder()
                .id(data.getId())
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .username(data.getUsername())
                .email(data.getEmail())
                .fullName(data.getFirstName() + " " + data.getLastName())
                .roles(GenerateRole.buildRoleResponseAsSet())
                .build();
    }

//    public static SignUpRequest buildSignUpRequest() {
//        Set<String> role = new HashSet<>();
//        role.add("USER");
//
//        return SignUpRequest.builder()
//                .firstName(RandomUtil.generateRandomString())
//                .lastName(RandomUtil.generateRandomString())
//                .username(RandomUtil.generateRandomString())
//                .email(RandomUtil.generateRandomString() + "test.com")
//                .password(RandomUtil.generateRandomString())
//                .roles(role).build();
//    }
}
