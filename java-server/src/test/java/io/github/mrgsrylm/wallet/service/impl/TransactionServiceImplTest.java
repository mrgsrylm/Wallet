package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.base.BaseServiceTest;
import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionRequest;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionRequestMapper;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionResponse;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionResponseMapper;
import io.github.mrgsrylm.wallet.exception.NoSuchElementFoundException;
import io.github.mrgsrylm.wallet.fixtures.GenerateTransaction;
import io.github.mrgsrylm.wallet.model.Transaction;
import io.github.mrgsrylm.wallet.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.util.Collections;
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
        Transaction mockTransaction = GenerateTransaction.build();
        Mockito.when(transactionRequestMapper.toEntity(Mockito.any(TransactionRequest.class)))
                .thenReturn(mockTransaction);
        Mockito.when(repository.save(Mockito.any(Transaction.class)))
                .thenReturn(mockTransaction);

        TransactionRequest request = GenerateTransaction.buildTransactionRequest();
        CommandResponse result = service.create(request);

        Assertions.assertNotNull(result);
        Mockito.verify(transactionRequestMapper, Mockito.times(1))
                .toEntity(Mockito.any(TransactionRequest.class));
        Mockito.verify(repository, Mockito.times(1))
                .save(Mockito.any(Transaction.class));
    }

    @Test
    void givenPageable_whenFindAll_ReturnSuccess() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        List<Transaction> mockTransactions = GenerateTransaction.buildList();
        Page<Transaction> mockPageTransactions = new PageImpl<>(mockTransactions, pageable, mockTransactions.size());
        TransactionResponse mockTransactionResponse = GenerateTransaction.buildTransactionResponse();

        Mockito.when(repository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(mockPageTransactions);
        Mockito.when(transactionResponseMapper.toDto((Mockito.any(Transaction.class))))
                .thenReturn(mockTransactionResponse);

        Page<TransactionResponse> result = service.findAll(pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
        Mockito.verify(transactionResponseMapper, Mockito.times(2))
                .toDto(Mockito.any(Transaction.class));
    }

    @Test
    void givenPageable_whenFindAll_ReturnException() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        Mockito.when(repository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(Page.empty());

        Assertions.assertThrows(NoSuchElementFoundException.class, () ->
                service.findAll(pageable));
    }

    @Test
    void givenUserId_whenFindAllByUserId_returnSucsess() {
        List<Transaction> transactions = GenerateTransaction.buildList();
        TransactionResponse mockTransactionResponse = GenerateTransaction.buildTransactionResponse();

        Mockito.when(repository.findAllByUserId(Mockito.anyLong()))
                .thenReturn(transactions);
        Mockito.when(transactionResponseMapper.toDto(Mockito.any(Transaction.class)))
                .thenReturn(mockTransactionResponse);

        List<TransactionResponse> result = service.findAllByUserId(mockTransactionResponse.getFromWallet().getUser().getId());

        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1))
                .findAllByUserId(Mockito.anyLong());
        Mockito.verify(transactionResponseMapper, Mockito.times(2))
                .toDto(Mockito.any(Transaction.class));
    }

    @Test
    void givenInvalidUserId_whenFindByUserId_ReturnException() {
        Mockito.when(repository.findAllByUserId(Mockito.anyLong()))
                .thenReturn(Collections.emptyList());

        Assertions.assertThrows(NoSuchElementFoundException.class, () ->
                service.findAllByUserId(1L));
    }

    @Test
    void givenId_whenFindById_returnSuccess() {
        Transaction mockTransaction = GenerateTransaction.build();
        TransactionResponse mockTransactionResponse = GenerateTransaction.buildTransactionResponse();
        mockTransactionResponse.setId(mockTransaction.getId());

        Mockito.when(repository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(mockTransaction));
        Mockito.when(transactionResponseMapper.toDto(Mockito.any(Transaction.class)))
                .thenReturn(mockTransactionResponse);

        TransactionResponse result = service.findById(mockTransaction.getId());

        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(transactionResponseMapper, Mockito.times(1))
                .toDto(Mockito.any(Transaction.class));
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
        Transaction mockTransaction = GenerateTransaction.build();
        TransactionResponse mockTransactionResponse = GenerateTransaction.buildTransactionResponse();
        mockTransactionResponse.setId(mockTransaction.getId());

        Mockito.when(repository.findByReferenceNumber(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(mockTransaction));
        Mockito.when(transactionResponseMapper.toDto(Mockito.any(Transaction.class)))
                .thenReturn(mockTransactionResponse);

        TransactionResponse result = service.findByReferenceNumber(mockTransaction.getReferenceNumber());

        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1))
                .findByReferenceNumber(Mockito.any(UUID.class));
        Mockito.verify(transactionResponseMapper, Mockito.times(1))
                .toDto(Mockito.any(Transaction.class));
    }

    @Test
    void givenInvalidReferenceNumber_whenFindByReferenceNumber_ReturnException() {
        Mockito.when(repository.findByReferenceNumber(Mockito.any(UUID.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementFoundException.class, () ->
                service.findByReferenceNumber(UUID.randomUUID()));
    }
}
