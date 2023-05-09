package com.poywallet.poywalletbackend.repository;

import com.poywallet.poywalletbackend.model.Role;
import com.poywallet.poywalletbackend.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> getReferenceByTypeIsIn(Set<RoleEnum> types);
}