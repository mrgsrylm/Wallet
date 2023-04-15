package com.pollspolling.backend.service;

import com.pollspolling.backend.delivery.payload.request.LoginRequest;
import com.pollspolling.backend.delivery.payload.request.RegistrationRequest;
import com.pollspolling.backend.delivery.payload.response.JwtAuthResponse;
import com.pollspolling.backend.delivery.result.CustomApiResponse;
import com.pollspolling.backend.domain.entity.User;

public interface AuthService {
    JwtAuthResponse authenticationUser(LoginRequest request);
    User registerUser(RegistrationRequest request);
}
