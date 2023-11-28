package io.github.mrgsrylm.wallet.service;

import io.github.mrgsrylm.wallet.model.RoleModel;
import io.github.mrgsrylm.wallet.model.enums.RoleType;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<RoleModel> getReferenceByTypeIsIn(Set<RoleType> types);

    List<RoleModel> findAll();
}
