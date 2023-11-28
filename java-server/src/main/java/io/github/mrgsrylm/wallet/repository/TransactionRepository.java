package io.github.mrgsrylm.wallet.repository;

import io.github.mrgsrylm.wallet.model.TransactionModel;
import io.github.mrgsrylm.wallet.model.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
    Optional<TransactionModel> findByReferenceNumber(UUID referenceNumber);

    List<TransactionModel> findByFromWallet(WalletModel fromWallet);

//    @Query(value = "SELECT t " +
//            "FROM TransactionModel t " +
//            "LEFT JOIN WalletModel w ON w.id IN t.fromWallet.id " +
//            "WHERE w.user.id = :userId " +
//            "ORDER BY t.createdAt DESC")
//    List<TransactionModel> findAllByUserId(@Param("userId") Long userId);
}
