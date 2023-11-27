package io.github.mrgsrylm.wallet.service;

import io.github.mrgsrylm.wallet.model.Role;
import io.github.mrgsrylm.wallet.model.enums.RoleType;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getReferenceByTypeIsIn(Set<RoleType> types);

    List<Role> findAll();
}
