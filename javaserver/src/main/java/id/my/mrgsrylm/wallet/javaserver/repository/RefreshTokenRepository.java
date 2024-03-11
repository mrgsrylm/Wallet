package id.my.mrgsrylm.wallet.javaserver.repository;

import id.my.mrgsrylm.wallet.javaserver.model.RefreshTokenModel;
import id.my.mrgsrylm.wallet.javaserver.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, Long> {
    RefreshTokenModel findByUserId(Long userId);

    Optional<RefreshTokenModel> findByToken(String token);

    @Modifying
    int deleteByUser(UserModel userModel);
}
