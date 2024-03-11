package id.my.mrgsrylm.wallet.javaserver.repository;

import id.my.mrgsrylm.wallet.javaserver.model.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletModel, Long> {
    Optional<WalletModel> findByIban(String iban);

    List<WalletModel> findByUserId(Long userId);

    WalletModel getReferenceByIban(String iban);

    boolean existsByIbanIgnoreCase(String iban);

    boolean existsByUserIdAndNameIgnoreCase(Long userId, String name);
}
