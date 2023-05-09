package com.poywallet.poywalletbackend.service.impl;

import com.poywallet.poywalletbackend.mapper.SignUpRequestMapper;
import com.poywallet.poywalletbackend.service.AuthService;
import com.poywallet.poywalletbackend.web.ResponseCommand;
import com.poywallet.poywalletbackend.web.api.auth.*;
import com.poywallet.poywalletbackend.exception.ElementAlreadyExistsException;
import com.poywallet.poywalletbackend.model.RoleEnum;
import com.poywallet.poywalletbackend.model.User;
import com.poywallet.poywalletbackend.repository.UserRepository;
import com.poywallet.poywalletbackend.security.UserDetailsImpl;
import com.poywallet.poywalletbackend.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.poywallet.poywalletbackend.common.Constants.*;

@Slf4j(topic = "AuthService")
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SignUpRequestMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public ResponseCommand signUp(RequestSignUp request) {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername().trim()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_USER_NAME);

        if (userRepository.existsByEmailIgnoreCase(request.getEmail().trim()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_USER_EMAIL);

        request.setRoles(Set.of(String.valueOf(RoleEnum.ROLE_USER)));
        final User user = mapper.toEntity(request);
        userRepository.save(user);
        log.info(CREATED_USER, user.getUsername());

        return ResponseCommand.builder().id(user.getId()).build();
    }

    @Override
    public ResponseAuth signIn(RequestSignIn request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword().trim())
        );
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        String jwt = jwtProvider.generateJwt(authentication);

        log.info(LOGGED_IN_USER, request.getUsername());
        return ResponseAuth
                .builder()
                .id(userDetails.getId())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .username(userDetails.getUsername())
                .roles(roles)
                .token(jwt)
                .build();

    }
}
