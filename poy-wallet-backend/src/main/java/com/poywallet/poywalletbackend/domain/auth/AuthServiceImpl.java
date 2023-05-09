package com.poywallet.poywalletbackend.domain.auth;

import com.poywallet.poywalletbackend.exception.ElementAlreadyExistsException;
import com.poywallet.poywalletbackend.domain.user.RoleEnum;
import com.poywallet.poywalletbackend.domain.user.User;
import com.poywallet.poywalletbackend.domain.user.UserRepository;
import com.poywallet.poywalletbackend.security.UserDetailsImpl;
import com.poywallet.poywalletbackend.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    private final JwtUtils jwtUtils;
    private final JwtRefreshService jwtRefreshService;

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
    public AuthResponse signIn(SignInRequest request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword().trim())
        );
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        String jwt = jwtUtils.generateJwt(authentication);
        JwtRefresh refreshJwt = jwtRefreshService.createJwtRefresh();


        log.info(LOGGED_IN_USER, request.getUsername());
        return AuthResponse
                .builder()
                .id(userDetails.getId())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .username(userDetails.getUsername())
                .roles(roles)
                .token(jwt).build();

    }

    @Override
    public AuthResponse refreshToken(JwtRefreshRequest request) {
        return null;
    }
}
