package com.poywallet.poywalletbackend.domain.user;

import com.poywallet.poywalletbackend.domain.user.Role;
import com.poywallet.poywalletbackend.domain.user.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> getReferenceByTypeIsIn(Set<RoleEnum> types);
}