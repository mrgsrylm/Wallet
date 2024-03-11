package id.my.mrgsrylm.wallet.javaserver.repository;

import id.my.mrgsrylm.wallet.javaserver.model.TransactionTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionTypeModel, Long> {
}
