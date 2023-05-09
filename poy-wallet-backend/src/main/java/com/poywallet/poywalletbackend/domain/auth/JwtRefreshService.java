package com.poywallet.poywalletbackend.domain.auth;

import com.poywallet.poywalletbackend.domain.auth.JwtRefresh;

public interface JwtRefreshService {
    JwtRefresh createJwtRefresh();
    void validateJwtRefresh(String token);
    void deleteJwtRefresh(String token);
}
