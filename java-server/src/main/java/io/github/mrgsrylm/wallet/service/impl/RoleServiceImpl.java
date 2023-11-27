package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.model.Role;
import io.github.mrgsrylm.wallet.model.enums.RoleType;
import io.github.mrgsrylm.wallet.repository.RoleRepository;
import io.github.mrgsrylm.wallet.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "RoleService")
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    /**
     * Fetches list of role (entity) by the given role types
     *
     * @param types
     * @return List of Role
     */
    public List<Role> getReferenceByTypeIsIn(Set<RoleType> types) {
        return roleRepository.getReferenceByTypeIsIn(types);
    }

    /**
     * Fetches all roles as entity
     *
     * @return List of Role
     */
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
