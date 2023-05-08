package com.poywallet.poywalletbackend.service;

import com.poywallet.poywalletbackend.dto.request.SignInRequest;
import com.poywallet.poywalletbackend.dto.request.SignUpRequest;
import com.poywallet.poywalletbackend.dto.response.CommandResponse;
import com.poywallet.poywalletbackend.dto.response.SignInResponse;

public interface AuthService {
    CommandResponse signUp(SignUpRequest request);
    SignInResponse signIn(SignInRequest request);

//    JwtResponse refreshToken(RefreshTokenRequest request);
}
