package com.poywallet.poywalletbackend.domain.user;

import com.poywallet.poywalletbackend.domain.user.Role;
import com.poywallet.poywalletbackend.domain.user.RoleEnum;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> findAll();
    List<Role> getReferenceByTypeIsIn(Set<RoleEnum> types);
}
