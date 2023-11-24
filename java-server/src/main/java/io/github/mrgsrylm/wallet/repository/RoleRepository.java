package io.github.mrgsrylm.wallet.repository;

import io.github.mrgsrylm.wallet.model.Role;
import io.github.mrgsrylm.wallet.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> getReferenceByTypeIsIn(Set<RoleType> types);
}
