package com.poywallet.poywalletbackend.service.impl;

import com.poywallet.poywalletbackend.dto.mapper.SignUpRequestMapper;
import com.poywallet.poywalletbackend.dto.request.SignInRequest;
import com.poywallet.poywalletbackend.dto.request.SignUpRequest;
import com.poywallet.poywalletbackend.dto.response.CommandResponse;
import com.poywallet.poywalletbackend.dto.response.SignInResponse;
import com.poywallet.poywalletbackend.exception.ElementAlreadyExistsException;
import com.poywallet.poywalletbackend.model.RoleEnum;
import com.poywallet.poywalletbackend.model.User;
import com.poywallet.poywalletbackend.repository.UserRepository;
import com.poywallet.poywalletbackend.security.UserDetailsImpl;
import com.poywallet.poywalletbackend.security.jwt.JwtProvider;
import com.poywallet.poywalletbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    @Override
    public CommandResponse signUp(SignUpRequest request) {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername().trim()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_USER_NAME);

        if (userRepository.existsByEmailIgnoreCase(request.getEmail().trim()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_USER_EMAIL);

        request.setRoles(Set.of(String.valueOf(RoleEnum.ROLE_USER)));
        final User user = mapper.toEntity(request);
        userRepository.save(user);
        log.info(CREATED_USER, user.getUsername());

        return CommandResponse.builder().id(user.getId()).build();
    }

    @Override
    public SignInResponse signIn(SignInRequest request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword().trim())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwt(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        log.info(LOGGED_IN_USER, request.getUsername());
        return SignInResponse
                .builder()
                .id(userDetails.getId())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .username(userDetails.getUsername())
                .roles(roles)
                .token(jwt).build();

    }

    /*
    @Override
    public JwtResponse refreshToken(RefreshTokenRequest request) {
        return null;
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenEnum.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    */
}
