package com.pollspolling.backend.repository;

import com.pollspolling.backend.domain.entity.Role;
import com.pollspolling.backend.domain.enumeration.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
