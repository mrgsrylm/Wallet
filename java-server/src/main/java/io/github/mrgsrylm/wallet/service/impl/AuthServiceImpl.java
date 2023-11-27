package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.auth.JwtResponse;
import io.github.mrgsrylm.wallet.dto.auth.LoginRequest;
import io.github.mrgsrylm.wallet.dto.auth.SignUpRequest;
import io.github.mrgsrylm.wallet.dto.auth.SignUpRequestMapper;
import io.github.mrgsrylm.wallet.exception.ElementAlreadyExistsException;
import io.github.mrgsrylm.wallet.exception.NoSuchElementFoundException;
import io.github.mrgsrylm.wallet.model.Role;
import io.github.mrgsrylm.wallet.model.User;
import io.github.mrgsrylm.wallet.repository.UserRepository;
import io.github.mrgsrylm.wallet.security.UserDetailsImpl;
import io.github.mrgsrylm.wallet.security.jwt.JwtUtils;
import io.github.mrgsrylm.wallet.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static io.github.mrgsrylm.wallet.common.Constants.*;

@Service
@Slf4j(topic = "AuthService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final SignUpRequestMapper signUpRequestMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public CommandResponse signup(SignUpRequest request) {
        if (repository.existsByUsernameIgnoreCase(request.getUsername().trim()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_USER_NAME);
        if (repository.existsByEmailIgnoreCase(request.getEmail().trim()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_USER_EMAIL);

        final User user = signUpRequestMapper.toEntity(request);
        log.info(CREATED_USER, user.getUsername());

        return CommandResponse.builder().id(user.getId()).build();
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword().trim());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_USERNAME));
        List<String> roles = user.getRoles().stream()
                        .map(item -> item.getType().getLabel()).toList();

        log.info(LOGGED_IN_USER, request.getUsername());
        return JwtResponse.builder()
                .token(jwt)
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .roles(roles)
                .build();
    }
}
