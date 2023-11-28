package io.github.mrgsrylm.wallet.repository;

import io.github.mrgsrylm.wallet.model.RoleModel;
import io.github.mrgsrylm.wallet.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    List<RoleModel> getReferenceByTypeIsIn(Set<RoleType> types);
}
