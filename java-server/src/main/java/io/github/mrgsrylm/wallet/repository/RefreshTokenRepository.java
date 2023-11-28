package io.github.mrgsrylm.wallet.repository;

import io.github.mrgsrylm.wallet.model.RefreshTokenModel;
import io.github.mrgsrylm.wallet.model.UserModel;
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
