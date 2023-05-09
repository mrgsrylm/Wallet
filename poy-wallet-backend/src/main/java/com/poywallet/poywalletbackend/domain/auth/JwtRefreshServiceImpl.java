package com.poywallet.poywalletbackend.domain.auth;

import com.poywallet.poywalletbackend.exception.NoSuchElementFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j(topic = "JwtRefreshService")
@Service
@RequiredArgsConstructor
public class JwtRefreshServiceImpl implements JwtRefreshService {
    private final JwtRefreshRepository jwtRefreshRepository;

    @Override
    public JwtRefresh createJwtRefresh() {
        var token = JwtRefresh.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(Instant.now())
                .build();

        return jwtRefreshRepository.save(token);
    }

    @Override
    public void validateJwtRefresh(String token) {
        Optional<JwtRefresh> result = jwtRefreshRepository.findByToken(token);
        if (result.isEmpty()) {
            throw new NoSuchElementFoundException();
        }
    }

    @Override
    public void deleteJwtRefresh(String token) {
        jwtRefreshRepository.deleteByToken(token);
    }
}
