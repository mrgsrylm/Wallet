package com.pollspolling.backend.service;

import com.pollspolling.backend.delivery.payload.request.LoginRequest;
import com.pollspolling.backend.delivery.payload.request.RegistrationRequest;
import com.pollspolling.backend.delivery.payload.response.JwtAuthResponse;
import com.pollspolling.backend.delivery.result.CustomApiResponse;
import com.pollspolling.backend.domain.entity.Role;
import com.pollspolling.backend.domain.entity.User;
import com.pollspolling.backend.domain.enumeration.RoleName;
import com.pollspolling.backend.exception.ApplicationException;
import com.pollspolling.backend.exception.BadRequestException;
import com.pollspolling.backend.exception.ResourceNotFoundException;
import com.pollspolling.backend.repository.RoleRepository;
import com.pollspolling.backend.repository.UserRepository;
import com.pollspolling.backend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public JwtAuthResponse authenticationUser(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = tokenProvider.generateToken(auth);

        return new JwtAuthResponse(jwt);
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new BadRequestException("Username already used");
        }

        if(userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email already used");
        }

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new ApplicationException("User Role not set."));

        User user = new User(
                request.getName(),
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        user.setRoles(Collections.singleton(userRole));

        User registered = userRepository.save(user);

        return registered;
    }
}
