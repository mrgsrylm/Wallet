package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.base.BaseServiceTest;
import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionRequest;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionRequestMapper;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionResponse;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionResponseMapper;
import io.github.mrgsrylm.wallet.exception.NoSuchElementFoundException;
import io.github.mrgsrylm.wallet.fixtures.GenerateTransaction;
import io.github.mrgsrylm.wallet.model.TransactionModel;
import io.github.mrgsrylm.wallet.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private TransactionServiceImpl service;

    @Mock
    private TransactionRepository repository;
    @Mock
    private TransactionRequestMapper transactionRequestMapper;
    @Mock
    private TransactionResponseMapper transactionResponseMapper;

    @Test
    void givenTransactionRequest_whenCreate_ReturnSuccess() {
        TransactionModel mockTransactionModel = GenerateTransaction.build();
        Mockito.when(transactionRequestMapper.toEntity(Mockito.any(TransactionRequest.class)))
                .thenReturn(mockTransactionModel);
        Mockito.when(repository.save(Mockito.any(TransactionModel.class)))
                .thenReturn(mockTransactionModel);

        TransactionRequest request = GenerateTransaction.buildTransactionRequest();
        CommandResponse result = service.create(request);

        Assertions.assertNotNull(result);
        Mockito.verify(transactionRequestMapper, Mockito.times(1))
                .toEntity(Mockito.any(TransactionRequest.class));
        Mockito.verify(repository, Mockito.times(1))
                .save(Mockito.any(TransactionModel.class));
    }

    @Test
    void givenPageable_whenFindAll_ReturnSuccess() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        List<TransactionModel> mockTransactionModels = GenerateTransaction.buildList();
        Page<TransactionModel> mockPageTransactions = new PageImpl<>(mockTransactionModels, pageable, mockTransactionModels.size());
        TransactionResponse mockTransactionResponse = GenerateTransaction.buildTransactionResponse();

        Mockito.when(repository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(mockPageTransactions);
        Mockito.when(transactionResponseMapper.toDto((Mockito.any(TransactionModel.class))))
                .thenReturn(mockTransactionResponse);

        Page<TransactionResponse> result = service.findAll(pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
        Mockito.verify(transactionResponseMapper, Mockito.times(2))
                .toDto(Mockito.any(TransactionModel.class));
    }

    @Test
    void givenPageable_whenFindAll_ReturnException() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        Mockito.when(repository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(Page.empty());

        Assertions.assertThrows(NoSuchElementFoundException.class, () ->
                service.findAll(pageable));
    }

//    @Test
//    void givenUserId_whenFindAllByUserId_returnSucsess() {
//        List<TransactionModel> transactionModels = GenerateTransaction.buildList();
//        TransactionResponse mockTransactionResponse = GenerateTransaction.buildTransactionResponse();
//
//        Mockito.when(repository.findAllByUserId(Mockito.anyLong()))
//                .thenReturn(transactionModels);
//        Mockito.when(transactionResponseMapper.toDto(Mockito.any(TransactionModel.class)))
//                .thenReturn(mockTransactionResponse);
//
//        List<TransactionResponse> result = service.findAllByUserId(mockTransactionResponse.getFromWallet().getUser().getId());
//
//        Assertions.assertNotNull(result);
//        Mockito.verify(repository, Mockito.times(1))
//                .findAllByUserId(Mockito.anyLong());
//        Mockito.verify(transactionResponseMapper, Mockito.times(2))
//                .toDto(Mockito.any(TransactionModel.class));
//    }
//
//    @Test
//    void givenInvalidUserId_whenFindByUserId_ReturnException() {
//        Mockito.when(repository.findAllByUserId(Mockito.anyLong()))
//                .thenReturn(Collections.emptyList());
//
//        Assertions.assertThrows(NoSuchElementFoundException.class, () ->
//                service.findAllByUserId(1L));
//    }

    @Test
    void givenId_whenFindById_returnSuccess() {
        TransactionModel mockTransactionModel = GenerateTransaction.build();
        TransactionResponse mockTransactionResponse = GenerateTransaction.buildTransactionResponse();
        mockTransactionResponse.setId(mockTransactionModel.getId());

        Mockito.when(repository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(mockTransactionModel));
        Mockito.when(transactionResponseMapper.toDto(Mockito.any(TransactionModel.class)))
                .thenReturn(mockTransactionResponse);

        TransactionResponse result = service.findById(mockTransactionModel.getId());

        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(transactionResponseMapper, Mockito.times(1))
                .toDto(Mockito.any(TransactionModel.class));
    }

    @Test
    void givenInvalidId_whenFindById_ReturnException() {
        Mockito.when(repository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementFoundException.class, () ->
                service.findById(1L));
    }

    @Test
    void givenReferenceId_whenFindByReferenceId_returnSuccess() {
        TransactionModel mockTransactionModel = GenerateTransaction.build();
        TransactionResponse mockTransactionResponse = GenerateTransaction.buildTransactionResponse();
        mockTransactionResponse.setId(mockTransactionModel.getId());

        Mockito.when(repository.findByReferenceNumber(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(mockTransactionModel));
        Mockito.when(transactionResponseMapper.toDto(Mockito.any(TransactionModel.class)))
                .thenReturn(mockTransactionResponse);

        TransactionResponse result = service.findByReferenceNumber(mockTransactionModel.getReferenceNumber());

        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1))
                .findByReferenceNumber(Mockito.any(UUID.class));
        Mockito.verify(transactionResponseMapper, Mockito.times(1))
                .toDto(Mockito.any(TransactionModel.class));
    }

    @Test
    void givenInvalidReferenceNumber_whenFindByReferenceNumber_ReturnException() {
        Mockito.when(repository.findByReferenceNumber(Mockito.any(UUID.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementFoundException.class, () ->
                service.findByReferenceNumber(UUID.randomUUID()));
    }
}
