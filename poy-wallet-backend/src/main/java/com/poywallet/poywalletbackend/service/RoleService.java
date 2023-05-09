package com.poywallet.poywalletbackend.service;

import com.poywallet.poywalletbackend.model.Role;
import com.poywallet.poywalletbackend.model.RoleEnum;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> findAll();
    List<Role> getReferenceByTypeIsIn(Set<RoleEnum> types);
}
