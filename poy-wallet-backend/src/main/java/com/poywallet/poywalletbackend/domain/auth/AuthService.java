package com.poywallet.poywalletbackend.domain.auth;

import com.poywallet.poywalletbackend.domain.auth.SignInRequest;
import com.poywallet.poywalletbackend.domain.auth.SignUpRequest;
import com.poywallet.poywalletbackend.domain.auth.CommandResponse;
import com.poywallet.poywalletbackend.domain.auth.AuthResponse;

public interface AuthService {
    CommandResponse signUp(SignUpRequest request);
    AuthResponse signIn(SignInRequest request);

    AuthResponse refreshToken(JwtRefreshRequest request);
}
