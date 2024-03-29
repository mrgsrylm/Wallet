package id.my.mrgsrylm.wallet.javaserver.controller;

import id.my.mrgsrylm.wallet.javaserver.base.BaseControllerTest;
import id.my.mrgsrylm.wallet.javaserver.dto.CommandResponse;
import id.my.mrgsrylm.wallet.javaserver.dto.auth.JwtResponse;
import id.my.mrgsrylm.wallet.javaserver.dto.auth.LoginRequest;
import id.my.mrgsrylm.wallet.javaserver.dto.auth.SignUpRequest;
import id.my.mrgsrylm.wallet.javaserver.model.enums.RoleType;
import id.my.mrgsrylm.wallet.javaserver.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest extends BaseControllerTest {
    @MockBean
    private AuthServiceImpl authService;

    @Test
    void givenSignUpRequest_WhenSignUp_ReturnSuccess() throws Exception {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .email("john.doe@example.com")
                .password("password123")
                .build();


        CommandResponse mockResponse = CommandResponse.builder().id(1L).build();

        Mockito.when(authService.signup(signUpRequest)).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void givenLoginRequest_WhenLogin_ReturnSuccess() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .username("johndoe")
                .password("password123").build();


        JwtResponse mockResponse = JwtResponse.builder()
                .id(1L)
                .firstName("Mock")
                .lastName("User")
                .username("mockedUsername")
                .type("Bearer")
                .token("yourMockedToken")
                .role(RoleType.USER.getLabel()).build();


        Mockito.when(authService.login(request)).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username", is("mockedUsername")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
