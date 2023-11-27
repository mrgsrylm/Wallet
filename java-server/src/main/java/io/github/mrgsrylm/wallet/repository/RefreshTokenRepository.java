package io.github.mrgsrylm.wallet.repository;

import io.github.mrgsrylm.wallet.model.RefreshToken;
import io.github.mrgsrylm.wallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUserId(Long userId);

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);
}
