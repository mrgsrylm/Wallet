package id.my.mrgsrylm.wallet.javaserver.service.impl;

import id.my.mrgsrylm.wallet.javaserver.base.BaseServiceTest;
import id.my.mrgsrylm.wallet.javaserver.dto.CommandResponse;
import id.my.mrgsrylm.wallet.javaserver.dto.auth.JwtResponse;
import id.my.mrgsrylm.wallet.javaserver.dto.auth.LoginRequest;
import id.my.mrgsrylm.wallet.javaserver.dto.auth.SignUpRequest;
import id.my.mrgsrylm.wallet.javaserver.dto.auth.SignUpRequestMapper;
import id.my.mrgsrylm.wallet.javaserver.exception.ElementAlreadyExistsException;
import id.my.mrgsrylm.wallet.javaserver.fixtures.GenerateUser;
import id.my.mrgsrylm.wallet.javaserver.model.UserModel;
import id.my.mrgsrylm.wallet.javaserver.repository.UserRepository;
import id.my.mrgsrylm.wallet.javaserver.security.jwt.JwtUtils;
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
    private SignUpRequestMapper signUpRequestMapper;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;

    @Test
    void givenSignUpRequest_WhenSignUp_ReturnSuccess() {
        SignUpRequest request = GenerateUser.buildSignUpRequest();
        UserModel mockUserModel = GenerateUser.buildUser();

        Mockito.when(userRepository.existsByUsernameIgnoreCase(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.existsByEmailIgnoreCase(Mockito.anyString())).thenReturn(false);
        Mockito.when(signUpRequestMapper.toEntity(Mockito.any(SignUpRequest.class))).thenReturn(mockUserModel);
        Mockito.when(userRepository.save(Mockito.any(UserModel.class))).thenReturn(mockUserModel);

        CommandResponse result = authService.signup(request);

        Assertions.assertEquals(result.id(), mockUserModel.getId());
        Mockito.verify(userRepository, Mockito.times(1))
                .existsByUsernameIgnoreCase(Mockito.anyString());
        Mockito.verify(userRepository, Mockito.times(1))
                .existsByEmailIgnoreCase(Mockito.anyString());
        Mockito.verify(signUpRequestMapper, Mockito.times(1))
                .toEntity(Mockito.any(SignUpRequest.class));
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.eq(mockUserModel));
    }

    @Test
    void givenSignUpRequest_WhenSignUpExistEmail_ReturnException() {
        SignUpRequest request = GenerateUser.buildSignUpRequest();

        Mockito.when(userRepository.existsByUsernameIgnoreCase(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.existsByEmailIgnoreCase(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(ElementAlreadyExistsException.class, () -> authService.signup(request));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(UserModel.class));
    }

    @Test
    void givenSignUpRequest_WhenSignUpExistUsername_ReturnException() {
        SignUpRequest request = GenerateUser.buildSignUpRequest();

        Mockito.when(userRepository.existsByUsernameIgnoreCase(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(ElementAlreadyExistsException.class, () -> authService.signup(request));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(UserModel.class));
    }

    @Test
    void givenLoginRequest_WhenLogin_ReturnSuccess() {
        UserModel mockUserModel = GenerateUser.buildUser();
        LoginRequest mockRequest  = LoginRequest.builder()
                .username(mockUserModel.getUsername()).password(mockUserModel.getPassword()).build();

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(mockRequest.getUsername(), mockRequest.getPassword());

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
        Mockito.when(jwtUtils.generateJwtToken(mockAuthentication)).thenReturn("mockedToken");
        Mockito.when(userRepository.findByUsername(mockRequest.getUsername())).thenReturn(Optional.of(mockUserModel));

        JwtResponse result = authService.login(mockRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockRequest.getUsername(), result.getUsername());
        Assertions.assertEquals(mockUserModel.getFirstName(), result.getFirstName());
        Assertions.assertEquals(mockUserModel.getLastName(), result.getLastName());
        Assertions.assertEquals(mockUserModel.getRole().getLabel(), result.getRole());
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
