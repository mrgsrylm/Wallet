package io.github.mrgsrylm.wallet.fixtures;

import io.github.mrgsrylm.wallet.dto.user.RoleResponse;
import io.github.mrgsrylm.wallet.fixtures.generator.RoleObjectGenerator;
import io.github.mrgsrylm.wallet.model.Role;
import io.github.mrgsrylm.wallet.model.enums.RoleType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenerateRole {
    public static Role build() {
        RoleObjectGenerator genRole = new RoleObjectGenerator();

        return genRole.generateRole();
    }

    public static Set<Role> buildAsSet() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(build());
        return roleSet;
    }

    public static List<Role> buildAsList() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(build());
        return roleList;
    }

    public static Set<RoleType> buildAsSetRoleType() {
        Set<RoleType> types = new HashSet<>();
        types.add(build().getType());

        return types;
    }

    public static Set<String> buildAsSetStringType() {
        String value = build().getType().getLabel();
        Set<String> roleName = new HashSet<>();
        roleName.add(value);
        return roleName;
    }

    public static RoleResponse buildRoleResponse() {
        Role role = build();
        return RoleResponse.builder()
                .id(role.getId())
                .type(role.getType()).build();
    }

    public static Set<RoleResponse> buildRoleResponseAsSet() {
        Set<RoleResponse> roles = new HashSet<>();
        roles.add(buildRoleResponse());
        return roles;
    }
}
