package io.github.mrgsrylm.wallet.service;

import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.auth.JwtResponse;
import io.github.mrgsrylm.wallet.dto.auth.LoginRequest;
import io.github.mrgsrylm.wallet.dto.auth.SignUpRequest;

public interface AuthService {
    JwtResponse login(LoginRequest request);

    CommandResponse signup(SignUpRequest request);
}
