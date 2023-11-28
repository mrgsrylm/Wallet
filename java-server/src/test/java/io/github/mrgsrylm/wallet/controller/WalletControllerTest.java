package io.github.mrgsrylm.wallet.controller;

import io.github.mrgsrylm.wallet.base.BaseControllerTest;
import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionRequest;
import io.github.mrgsrylm.wallet.dto.wallet.WalletRequest;
import io.github.mrgsrylm.wallet.dto.wallet.WalletResponse;
import io.github.mrgsrylm.wallet.fixtures.GenerateTransaction;
import io.github.mrgsrylm.wallet.fixtures.GenerateWallet;
import io.github.mrgsrylm.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WalletControllerTest extends BaseControllerTest {
    @MockBean
    private WalletService walletService;

    @Test
    void givenIdRequest_WhenFindByID_ReturnSuccess() throws Exception {
        WalletResponse mockResponse = GenerateWallet.buildWalletResponse();
        mockResponse.setId(1L);

        Mockito.when(walletService.findById(mockResponse.getId())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/wallets/{id}", mockResponse.getId())
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(mockResponse.getId().intValue())));
    }

    @Test
    void givenIbanRequest_WhenFindByIban_ReturnSuccess() throws Exception {
        WalletResponse mockResponse = GenerateWallet.buildWalletResponse();
        mockResponse.setId(1L);

        Mockito.when(walletService.findByIban(mockResponse.getIban())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/wallets/iban/{iban}", mockResponse.getIban())
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.iban", is(mockResponse.getIban())));
    }

    @Test
    void givenUserIdRequest_WhenFindByUserId_ReturnSuccess() throws Exception {
        WalletResponse mockResponse = GenerateWallet.buildWalletResponse();
        mockResponse.getUser().setId(1L);
        mockResponse.setId(1L);
        List<WalletResponse> mockListResponse = new ArrayList<>();
        mockListResponse.add(mockResponse);

        Mockito.when(walletService.findByUserId(mockResponse.getUser().getId())).thenReturn(mockListResponse);

        mockMvc.perform(get("/api/v1/wallets/users/{userId}", mockResponse.getUser().getId())
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.data.user.id", is(mockResponse.getUser().getId())));
    }

    @Test
    void givenNoParam_WhenFindByAll_ReturnSuccess() throws Exception {
        WalletResponse mockResponse = GenerateWallet.buildWalletResponse();
        mockResponse.getUser().setId(1L);
        mockResponse.setId(1L);
        List<WalletResponse> mockListResponse = new ArrayList<>();
        mockListResponse.add(mockResponse);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        Page<WalletResponse> mockPageResponse = new PageImpl<>(mockListResponse, pageable, mockListResponse.size());

        Mockito.when(walletService.findAll(pageable)).thenReturn(mockPageResponse);

        mockMvc.perform(get("/api/v1/wallets")
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenWalletRequest_WhenCreate_ReturnSuccess() throws Exception {
        WalletRequest mockRequest = GenerateWallet.buildWalletRequest();
        CommandResponse mockResponse = CommandResponse.builder().id(1L).build();

        Mockito.when(walletService.create(mockRequest)).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/wallets")
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void givenTransactionRequest_WhenAddFunds_ReturnSuccess() throws Exception {
        TransactionRequest mockRequest = GenerateTransaction.buildTransactionRequest();
        CommandResponse mockResponse = CommandResponse.builder().id(1L).build();

        Mockito.when(walletService.addFunds(mockRequest)).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/wallets/addFunds")
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void givenTransactionRequest_WhenWithDrawFunds_ReturnSuccess() throws Exception {
        TransactionRequest mockRequest = GenerateTransaction.buildTransactionRequest();
        CommandResponse mockResponse = CommandResponse.builder().id(1L).build();

        Mockito.when(walletService.withdrawFunds(mockRequest)).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/wallets/withdrawFunds")
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void givenWalletRequest_WhenUpdate_ReturnSuccess() throws Exception {
        WalletRequest mockRequest = GenerateWallet.buildWalletRequest();
        CommandResponse mockResponse = CommandResponse.builder().id(1L).build();

        Mockito.when(walletService.update(mockRequest)).thenReturn(mockResponse);

        mockMvc.perform(put("/api/v1/wallets")
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteById() throws Exception {
        Mockito.doNothing().when(walletService).deleteById(1);

        mockMvc.perform(delete("/api/v1/wallets/{id}", 1)
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}