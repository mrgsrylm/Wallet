package id.my.mrgsrylm.wallet.javaserver.service;

import id.my.mrgsrylm.wallet.javaserver.dto.CommandResponse;
import id.my.mrgsrylm.wallet.javaserver.dto.auth.JwtResponse;
import id.my.mrgsrylm.wallet.javaserver.dto.auth.LoginRequest;
import id.my.mrgsrylm.wallet.javaserver.dto.auth.SignUpRequest;

public interface AuthService {
    JwtResponse login(LoginRequest request);

    CommandResponse signup(SignUpRequest request);
}
