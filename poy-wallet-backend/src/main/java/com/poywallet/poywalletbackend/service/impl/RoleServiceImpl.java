package com.poywallet.poywalletbackend.service.impl;

import com.poywallet.poywalletbackend.model.Role;
import com.poywallet.poywalletbackend.model.RoleEnum;
import com.poywallet.poywalletbackend.repository.RoleRepository;
import com.poywallet.poywalletbackend.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Service used for Role related operations
 */
@Slf4j(topic = "RoleService")
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    /**
     * Fetches all roles as entity
     *
     * @return List of Role
     */
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * Fetches list of role (entity) by the given role types
     *
     * @param types
     * @return List of Role
     */
    @Override
    public List<Role> getReferenceByTypeIsIn(Set<RoleEnum> types) {
        return roleRepository.getReferenceByTypeIsIn(types);
    }
}
