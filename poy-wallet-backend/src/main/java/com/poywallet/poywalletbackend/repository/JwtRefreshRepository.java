package com.poywallet.poywalletbackend.repository;

import com.poywallet.poywalletbackend.model.JwtRefresh;
import com.poywallet.poywalletbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRefreshRepository extends JpaRepository<JwtRefresh, Integer> {
    Optional<JwtRefresh> findByToken(String token);

    void deleteByToken(String token);

    @Modifying
    int deleteByUser(User user);
}
