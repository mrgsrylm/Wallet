package com.poywallet.poywalletbackend.domain.auth;

import com.poywallet.poywalletbackend.domain.auth.JwtRefresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRefreshRepository extends JpaRepository<JwtRefresh, Integer> {
    Optional<JwtRefresh> findByToken(String token);

    void deleteByToken(String token);

//    @Query(value = """
//            select t from Token t inner join User u\s
//            on t.user.id = u.id\s
//            where u.id = :id and (t.expired = false or t.revoked = false)\s
//            """)
//    List<JwtRefresh> findAllValidTokenByUser(Long id);
}
