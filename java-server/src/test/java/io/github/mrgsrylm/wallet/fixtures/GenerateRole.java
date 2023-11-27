package io.github.mrgsrylm.wallet.fixtures;

import io.github.mrgsrylm.wallet.model.Role;
import io.github.mrgsrylm.wallet.model.enums.RoleType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenerateRole {
    public static Role buildRole() {
        Role role = new Role();
        role.setId(1L);
        role.setType(RoleType.ROLE_USER);
        return role;
    }

    public static Set<Role> buildAsSet() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(buildRole());
        return roleSet;
    }

    public static List<Role> buildAsList() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(buildRole());
        return roleList;
    }
}
