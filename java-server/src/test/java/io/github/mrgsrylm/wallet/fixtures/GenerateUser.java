package io.github.mrgsrylm.wallet.fixtures;

import io.github.mrgsrylm.wallet.dto.auth.SignUpRequest;
import io.github.mrgsrylm.wallet.dto.user.UserResponse;
import io.github.mrgsrylm.wallet.fixtures.generator.UserObjectGenerator;
import io.github.mrgsrylm.wallet.model.UserModel;

public class GenerateUser {
    public static UserModel build() {
        UserObjectGenerator generator = new UserObjectGenerator();

        return generator.generateUser(GenerateRole.buildAsSet());
    }

    public static SignUpRequest buildSignUpRequest() {
        UserModel userModel = build();

        return SignUpRequest.builder()
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .username(userModel.getUsername())
                .email(userModel.getEmail())
                .password(userModel.getPassword())
                .roles(GenerateRole.buildAsSetStringType()).build();
    }

    public static UserResponse buildUserResponse() {
        UserModel data = build();
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
