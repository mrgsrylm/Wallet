package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.auth.JwtResponse;
import io.github.mrgsrylm.wallet.dto.auth.LoginRequest;
import io.github.mrgsrylm.wallet.dto.auth.SignUpRequest;
import io.github.mrgsrylm.wallet.dto.auth.SignUpRequestMapper;
import io.github.mrgsrylm.wallet.exception.ElementAlreadyExistsException;
import io.github.mrgsrylm.wallet.exception.NoSuchElementFoundException;
import io.github.mrgsrylm.wallet.model.UserModel;
import io.github.mrgsrylm.wallet.model.enums.RoleType;
import io.github.mrgsrylm.wallet.repository.UserRepository;
import io.github.mrgsrylm.wallet.security.jwt.JwtUtils;
import io.github.mrgsrylm.wallet.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        final UserModel userModel = signUpRequestMapper.toEntity(request);
        userModel.setRole(RoleType.USER);

        repository.save(userModel);
        log.info(CREATED_USER, userModel.getUsername());

        return CommandResponse.builder().id(userModel.getId()).build();
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserModel userModel = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_USERNAME));

        log.info(LOGGED_IN_USER, request.getUsername());
        return JwtResponse.builder()
                .token(jwt)
                .id(userModel.getId())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .username(userModel.getUsername())
                .role(userModel.getRole().getLabel())
                .build();
    }
}
