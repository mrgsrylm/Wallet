package io.github.mrgsrylm.wallet.repository;

import io.github.mrgsrylm.wallet.model.TransactionTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionTypeModel, Long> {
}
