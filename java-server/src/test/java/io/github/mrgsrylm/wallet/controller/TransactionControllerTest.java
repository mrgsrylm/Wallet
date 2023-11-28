package io.github.mrgsrylm.wallet.controller;

import io.github.mrgsrylm.wallet.base.BaseControllerTest;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionResponse;
import io.github.mrgsrylm.wallet.fixtures.GenerateTransaction;
import io.github.mrgsrylm.wallet.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionControllerTest extends BaseControllerTest {
    @MockBean
    private TransactionService transactionService;

    @Test
    void givenIdRequest_WhenFindByID_ReturnSuccess() throws Exception {
        TransactionResponse mockResponse = GenerateTransaction.buildTransactionResponse();

        Mockito.when(transactionService.findById(mockResponse.getId())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/transactions/{id}", mockResponse.getId())
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(mockResponse.getId().intValue())));
    }

    @Test
    void givenReferenceNumberRequest_WhenFindByReferenceNumber_ReturnSuccess() throws Exception {
        TransactionResponse mockResponse = GenerateTransaction.buildTransactionResponse();

        Mockito.when(transactionService.findByReferenceNumber(mockResponse.getReferenceNumber())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/transactions/references/{referenceNumber}", mockResponse.getReferenceNumber())
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.referenceNumber", is(mockResponse.getReferenceNumber().toString())));
    }


//    void givenUserIdRequest_WhenFindByUserIdNumber_ReturnSuccess() throws Exception {
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
//        TransactionResponse mockTransaction = GenerateTransaction.buildTransactionResponse();
//        Long userId = mockTransaction.getFromWallet().getUser().getId();
//        List<TransactionResponse> mockListTransactions = new ArrayList<>();
//        mockListTransactions.add(mockTransaction);
//
//        Mockito.when(transactionService.findAllByUserId(userId)).thenReturn(mockListTransactions);
//
//        mockMvc.perform(get("/api/v1/transactions/users/{userId}",userId)
//                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

    @Test
    void givenPageable_WhenFindAll_ReturnSuccess() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        List<TransactionResponse> mockListTransactions = GenerateTransaction.buildListTransactionResponse();
        Page<TransactionResponse> mockPageTransaction = new PageImpl<>(mockListTransactions, pageable, mockListTransactions.size());

        Mockito.when(transactionService.findAll(pageable)).thenReturn(mockPageTransaction);

        mockMvc.perform(get("/api/v1/transactions",pageable)
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}