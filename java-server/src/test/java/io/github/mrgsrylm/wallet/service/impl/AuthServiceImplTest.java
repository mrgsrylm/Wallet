package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.base.BaseServiceTest;
import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.auth.JwtResponse;
import io.github.mrgsrylm.wallet.dto.auth.LoginRequest;
import io.github.mrgsrylm.wallet.dto.auth.SignUpRequest;
import io.github.mrgsrylm.wallet.dto.auth.SignUpRequestMapper;
import io.github.mrgsrylm.wallet.exception.ElementAlreadyExistsException;
import io.github.mrgsrylm.wallet.fixtures.GenerateUser;
import io.github.mrgsrylm.wallet.model.User;
import io.github.mrgsrylm.wallet.repository.UserRepository;
import io.github.mrgsrylm.wallet.security.jwt.JwtUtils;
import io.github.mrgsrylm.wallet.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

class AuthServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;
    @Mock
    private SignUpRequestMapper signUpRequestMapper;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;

    @Test
    void givenSignUpRequest_WhenSignUp_ReturnSuccess() {
        SignUpRequest request = GenerateUser.buildSignUpRequest();
        User mockUser = GenerateUser.build();

        Mockito.when(userRepository.existsByUsernameIgnoreCase(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.existsByEmailIgnoreCase(Mockito.anyString())).thenReturn(false);
        Mockito.when(signUpRequestMapper.toEntity(Mockito.any(SignUpRequest.class))).thenReturn(mockUser);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUser);

        CommandResponse result = authService.signup(request);

        Assertions.assertEquals(result.id(), mockUser.getId());
        Mockito.verify(userRepository, Mockito.times(1))
                .existsByUsernameIgnoreCase(Mockito.anyString());
        Mockito.verify(userRepository, Mockito.times(1))
                .existsByEmailIgnoreCase(Mockito.anyString());
        Mockito.verify(signUpRequestMapper, Mockito.times(1))
                .toEntity(Mockito.any(SignUpRequest.class));
        // Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.eq(mockUser));
    }

    @Test
    void givenSignUpRequest_WhenSignUpExistEmail_ReturnException() {
        SignUpRequest request = GenerateUser.buildSignUpRequest();

        Mockito.when(userRepository.existsByUsernameIgnoreCase(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.existsByEmailIgnoreCase(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(ElementAlreadyExistsException.class, () -> authService.signup(request));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    void givenSignUpRequest_WhenSignUpExistUsername_ReturnException() {
        SignUpRequest request = GenerateUser.buildSignUpRequest();

        Mockito.when(userRepository.existsByUsernameIgnoreCase(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(ElementAlreadyExistsException.class, () -> authService.signup(request));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    void givenLoginRequest_WhenLogin_ReturnSuccess() {
        User mockUser = GenerateUser.build();
        LoginRequest mockRequest  = LoginRequest.builder()
                .username(mockUser.getUsername()).password(mockUser.getPassword()).build();

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(mockRequest.getUsername(), mockRequest.getPassword());

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
        Mockito.when(jwtUtils.generateJwtToken(mockAuthentication)).thenReturn("mockedToken");
        Mockito.when(userRepository.findByUsername(mockRequest.getUsername())).thenReturn(Optional.of(mockUser));

        JwtResponse result = authService.login(mockRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockRequest.getUsername(), result.getUsername());
        Assertions.assertEquals(mockUser.getFirstName(), result.getFirstName());
        Assertions.assertEquals(mockUser.getLastName(), result.getLastName());
        Assertions.assertEquals(mockUser.getRoles().stream().map(item -> item.getType().getLabel()).toList(), result.getRoles());
        Assertions.assertEquals("mockedToken", result.getToken());
    }

    @Test
    void givenLoginRequest_WhenLogin_ReturnException() {
        LoginRequest mockRequest  = LoginRequest.builder()
                .username("John Doe").password("Password123").build();

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        Assertions.assertThrows(RuntimeException.class, () -> authService.login(mockRequest));
    }
}
