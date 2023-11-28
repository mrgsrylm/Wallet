package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.base.BaseServiceTest;
import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionRequest;
import io.github.mrgsrylm.wallet.dto.wallet.*;
import io.github.mrgsrylm.wallet.exception.ElementAlreadyExistsException;
import io.github.mrgsrylm.wallet.exception.NoSuchElementFoundException;
import io.github.mrgsrylm.wallet.fixtures.GenerateTransaction;
import io.github.mrgsrylm.wallet.fixtures.GenerateWallet;
import io.github.mrgsrylm.wallet.model.WalletModel;
import io.github.mrgsrylm.wallet.repository.WalletRepository;
import io.github.mrgsrylm.wallet.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class WalletServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private WalletServiceImpl service;

    @Mock
    private WalletRepository walletRepository;
    @Mock
    private TransactionService transactionService;
    @Mock
    private WalletResponseMapper walletResponseMapper;
    @Mock
    private WalletRequestMapper walletRequestMapper;
    @Mock
    private WalletTransactionRequestMapper walletTransactionRequestMapper;

    @Test
    void givenWalletRequest_whenCreate_ReturnSuccess() {
        WalletRequest request = GenerateWallet.buildWalletRequest();
        WalletModel mockWalletModel = GenerateWallet.build();
        TransactionRequest mockTransRequest = GenerateTransaction.buildTransactionRequest();
        CommandResponse transactionId = CommandResponse.builder().id(1L).build();

        Mockito.when(walletRepository.existsByIbanIgnoreCase(Mockito.anyString()))
                .thenReturn(false);
        Mockito.when(walletRepository.existsByUserIdAndNameIgnoreCase(Mockito.anyLong(), Mockito.anyString()))
                .thenReturn(false);
        Mockito.when(walletRequestMapper.toEntity(Mockito.any(WalletRequest.class)))
                .thenReturn(mockWalletModel);
        Mockito.when(walletRepository.save(Mockito.any(WalletModel.class)))
                .thenReturn(mockWalletModel);
        Mockito.when(walletTransactionRequestMapper.toTransactionDto(Mockito.any(WalletRequest.class)))
                        .thenReturn(mockTransRequest);
        Mockito.when(transactionService.create(Mockito.any(TransactionRequest.class)))
                .thenReturn(transactionId);

        CommandResponse result = service.create(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockWalletModel.getId(), result.id());
        Mockito.verify(walletRepository, Mockito.times(1))
                .existsByIbanIgnoreCase(Mockito.anyString());
        Mockito.verify(walletRepository, Mockito.times(1))
                .existsByUserIdAndNameIgnoreCase(Mockito.anyLong(), Mockito.anyString());
        Mockito.verify(walletRequestMapper, Mockito.times(1))
                .toEntity(Mockito.any(WalletRequest.class));
        Mockito.verify(walletRepository, Mockito.times(1))
                .save(Mockito.any(WalletModel.class));
        Mockito.verify(walletTransactionRequestMapper, Mockito.times(1))
                .toTransactionDto(Mockito.any(WalletRequest.class));
        Mockito.verify(transactionService, Mockito.times(1))
                .create(Mockito.any(TransactionRequest.class));
    }

    @Test
    void givenWalletRequest_whenCreate_ReturnExceptionIgnoreIban() {
        WalletRequest request = GenerateWallet.buildWalletRequest();

        Mockito.when(walletRepository.existsByIbanIgnoreCase(Mockito.anyString()))
                .thenReturn(true);

        Assertions.assertThrows(ElementAlreadyExistsException.class, () -> service.create(request));
    }

    @Test
    void givenWalletRequest_whenCreate_ReturnExceptionIgnoreUserAndName() {
        WalletRequest request = GenerateWallet.buildWalletRequest();

        Mockito.when(walletRepository.existsByIbanIgnoreCase(Mockito.anyString()))
                .thenReturn(false);
        Mockito.when(walletRepository.existsByUserIdAndNameIgnoreCase(Mockito.anyLong(), Mockito.anyString()))
                .thenReturn(true);

        Assertions.assertThrows(ElementAlreadyExistsException.class, () -> service.create(request));
    }

    @Test
    void givenTransactionRequest_whenAddFunds_ReturnSuccess() {
        TransactionRequest request = GenerateTransaction.buildTransactionRequest();
        WalletModel sender = GenerateWallet.build();
        WalletModel receiver = GenerateWallet.build();
        CommandResponse transactionId = CommandResponse.builder().id(request.getId()).build();

        Mockito.when(walletRepository.findByIban(Mockito.anyString()))
                .thenReturn(Optional.of(receiver));
        Mockito.when(walletRepository.save(Mockito.any(WalletModel.class)))
                .thenReturn(receiver);
        Mockito.when(transactionService.create(Mockito.any(TransactionRequest.class)))
                .thenReturn(transactionId);

        CommandResponse result = service.addFunds(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.getId(), result.id());
        Mockito.verify(walletRepository, Mockito.times(1))
                .findByIban(Mockito.anyString());
        Mockito.verify(walletRepository, Mockito.times(1))
                .save(Mockito.any(WalletModel.class));
        Mockito.verify(transactionService, Mockito.times(1))
                .create(Mockito.any(TransactionRequest.class));
    }

    @Test
    void givenTransactionRequest_whenTransferFunds_ReturnSuccess() {
        TransactionRequest request = GenerateTransaction.buildTransactionRequest();
        WalletModel sender = GenerateWallet.build();
        WalletModel receiver = GenerateWallet.build();
        CommandResponse transactionId = CommandResponse.builder().id(request.getId()).build();

        Mockito.when(walletRepository.findByIban(Mockito.anyString()))
                .thenReturn(Optional.of(sender));
        Mockito.when(walletRepository.findByIban(Mockito.anyString()))
                .thenReturn(Optional.of(receiver));
        Mockito.when(walletRepository.save(Mockito.any(WalletModel.class)))
                .thenReturn(receiver);
        Mockito.when(transactionService.create(Mockito.any(TransactionRequest.class)))
                .thenReturn(transactionId);

        CommandResponse result = service.transferFunds(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.getId(), result.id());
        Mockito.verify(walletRepository, Mockito.times(2))
                .findByIban(Mockito.anyString());
        Mockito.verify(walletRepository, Mockito.times(1))
                .save(Mockito.any(WalletModel.class));
        Mockito.verify(transactionService, Mockito.times(1))
                .create(Mockito.any(TransactionRequest.class));
    }

    @Test
    void givenTransactionRequest_whenWithdrawFunds_ReturnSuccess() {
        TransactionRequest request = GenerateTransaction.buildTransactionRequest();
        WalletModel sender = GenerateWallet.build();
        CommandResponse transactionId = CommandResponse.builder().id(request.getId()).build();

        Mockito.when(walletRepository.findByIban(Mockito.anyString()))
                .thenReturn(Optional.of(sender));
        Mockito.when(walletRepository.save(Mockito.any(WalletModel.class)))
                .thenReturn(sender);
        Mockito.when(transactionService.create(Mockito.any(TransactionRequest.class)))
                .thenReturn(transactionId);

        CommandResponse result = service.withdrawFunds(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.getId(), result.id());
        Mockito.verify(walletRepository, Mockito.times(1))
                .findByIban(Mockito.anyString());
        Mockito.verify(walletRepository, Mockito.times(1))
                .save(Mockito.any(WalletModel.class));
        Mockito.verify(transactionService, Mockito.times(1))
                .create(Mockito.any(TransactionRequest.class));
    }

    @Test
    void givenNoParam_whenFindAll_ReturnSuccess() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        List<WalletModel> walletModels = GenerateWallet.buildListWallet();
        Page<WalletModel> mockPageWallets = new PageImpl<>(walletModels, pageable, walletModels.size());

        WalletResponse mockWalletResponse = GenerateWallet.buildWalletResponse();

        Mockito.when(walletRepository.findAll(Mockito.any(Pageable.class))).thenReturn(mockPageWallets);
        Mockito.when(walletResponseMapper.toDto(Mockito.any(WalletModel.class))).thenReturn(mockWalletResponse);


        Page<WalletResponse> result = service.findAll(pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(walletRepository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
        Mockito.verify(walletResponseMapper, Mockito.times(2))
                .toDto(Mockito.any(WalletModel.class));
    }

    @Test
    void givenNoParam_whenFindAll_ReturnException() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        Mockito.when(walletRepository.findAll(Mockito.any(Pageable.class))).thenReturn(Page.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> service.findAll(pageable));
    }

    @Test
    void givenUserId_whenFindByUserId_ReturnSuccess() {
        WalletResponse mockWalletResponse = GenerateWallet.buildWalletResponse();
        List<WalletModel> mockListWalletModel = GenerateWallet.buildListWallet();

        Mockito.when(walletRepository.findByUserId(Mockito.anyLong())).thenReturn(mockListWalletModel);
        Mockito.when(walletResponseMapper.toDto(Mockito.any(WalletModel.class))).thenReturn(mockWalletResponse);


        List<WalletResponse> result = service.findByUserId(mockWalletResponse.getUser().getId());

        Assertions.assertNotNull(result);
        Mockito.verify(walletRepository, Mockito.times(1))
                .findByUserId(Mockito.anyLong());
        Mockito.verify(walletResponseMapper, Mockito.times(2))
                .toDto(Mockito.any(WalletModel.class));
    }

    @Test
    void givenID_whenFindById_ReturnSuccess() {
        WalletResponse mockWalletResponse = GenerateWallet.buildWalletResponse();

        WalletModel mockWalletModel = GenerateWallet.build();

        Mockito.when(walletRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockWalletModel));
        Mockito.when(walletResponseMapper.toDto(Mockito.any(WalletModel.class))).thenReturn(mockWalletResponse);


        WalletResponse result = service.findById(mockWalletResponse.getId());

        Assertions.assertNotNull(result);
        Mockito.verify(walletRepository, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(walletResponseMapper, Mockito.times(1))
                .toDto(Mockito.any(WalletModel.class));
    }

    @Test
    void givenInValidID_whenFindById_ReturnException() {
        Mockito.when(walletRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> service.findById(1L));
    }

    @Test
    void givenIban_whenFindByIban_ReturnSuccess() {
        WalletResponse mockWalletResponse = GenerateWallet.buildWalletResponse();

        WalletModel mockWalletModel = GenerateWallet.build();

        Mockito.when(walletRepository.findByIban(Mockito.anyString())).thenReturn(Optional.of(mockWalletModel));
        Mockito.when(walletResponseMapper.toDto(Mockito.any(WalletModel.class))).thenReturn(mockWalletResponse);


        WalletResponse result = service.findByIban(mockWalletResponse.getIban());

        Assertions.assertNotNull(result);
        Mockito.verify(walletRepository, Mockito.times(1))
                .findByIban(Mockito.anyString());
        Mockito.verify(walletResponseMapper, Mockito.times(1))
                .toDto(Mockito.any(WalletModel.class));
    }

    @Test
    void givenInValidIban_whenFindByIban_ReturnException() {
        Mockito.when(walletRepository.findByIban(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> service.findByIban("1234"));
    }

    @Test
    void givenIban_whenGetByIban_ReturnSuccess() {
        WalletModel mockWalletModel = GenerateWallet.build();

        Mockito.when(walletRepository.findByIban(Mockito.anyString())).thenReturn(Optional.of(mockWalletModel));

        WalletModel result = service.getByIban(mockWalletModel.getIban());

        Assertions.assertNotNull(result);
        Mockito.verify(walletRepository, Mockito.times(1))
                .findByIban(Mockito.anyString());
    }

    @Test
    void givenInValidIban_whenGetByIban_ReturnException() {
        Mockito.when(walletRepository.findByIban(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> service.findByIban("1234"));
    }

    @Test
    void givenWalletRequest_whenUpdate_ReturnSuccess() {
        WalletModel mockWalletModel = GenerateWallet.build();
        WalletRequest request = GenerateWallet.buildWalletRequest();
        request.setId(mockWalletModel.getId());

        Mockito.when(walletRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(mockWalletModel));
        Mockito.when(walletRepository.existsByIbanIgnoreCase(Mockito.anyString()))
                .thenReturn(false);
        Mockito.when(walletRepository.existsByUserIdAndNameIgnoreCase(Mockito.anyLong(), Mockito.anyString()))
                .thenReturn(false);
        Mockito.when(walletRequestMapper.toEntity(Mockito.any(WalletRequest.class)))
                .thenReturn(mockWalletModel);
        Mockito.when(walletRepository.save(Mockito.any(WalletModel.class)))
                .thenReturn(mockWalletModel);

        CommandResponse result = service.update(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.getId(), result.id());
        Mockito.verify(walletRepository, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(walletRepository, Mockito.times(1))
                .existsByIbanIgnoreCase(Mockito.anyString());
        Mockito.verify(walletRepository, Mockito.times(1))
                .existsByUserIdAndNameIgnoreCase(Mockito.anyLong(), Mockito.anyString());
        Mockito.verify(walletRequestMapper, Mockito.times(1))
                .toEntity(Mockito.any(WalletRequest.class));
        Mockito.verify(walletRepository, Mockito.times(1))
                .save(Mockito.any(WalletModel.class));
    }

    @Test
    void givenWalletRequest_whenUpdate_ReturnFailedCauseId() {
        WalletRequest request = GenerateWallet.buildWalletRequest();

        Mockito.when(walletRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementFoundException.class, () -> service.update(request));
    }

    @Test
    void givenWalletRequest_whenUpdate_ReturnFailedCauseIban() {
        WalletModel mockWalletModel = GenerateWallet.build();
        WalletRequest request = GenerateWallet.buildWalletRequest();
        request.setId(mockWalletModel.getId());

        Mockito.when(walletRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(mockWalletModel));
        Mockito.when(walletRepository.existsByIbanIgnoreCase(Mockito.anyString()))
                .thenReturn(true);

        Assertions.assertThrows(ElementAlreadyExistsException.class, () -> service.update(request));
    }

    @Test
    void givenWalletRequest_whenUpdate_ReturnFailedCauseUserIdAndName() {
        WalletModel mockWalletModel = GenerateWallet.build();
        WalletRequest request = GenerateWallet.buildWalletRequest();
        request.setId(mockWalletModel.getId());

        Mockito.when(walletRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(mockWalletModel));
        Mockito.when(walletRepository.existsByIbanIgnoreCase(Mockito.anyString()))
                .thenReturn(false);
        Mockito.when(walletRepository.existsByUserIdAndNameIgnoreCase(Mockito.anyLong(), Mockito.anyString()))
                .thenReturn(true);

        Assertions.assertThrows(ElementAlreadyExistsException.class, () -> service.update(request));
    }

    @Test
    void givenIDRequest_whenDeleteById_ReturnSuccess() {
        WalletModel mockWalletModel = GenerateWallet.build();
        WalletRequest request = GenerateWallet.buildWalletRequest();
        request.setId(mockWalletModel.getId());

        Mockito.when(walletRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(mockWalletModel));
        Mockito.doNothing().when(walletRepository).delete(Mockito.any(WalletModel.class));

        service.deleteById(mockWalletModel.getId());

        Mockito.verify(walletRepository, Mockito.times(1)).delete(Mockito.any(WalletModel.class));
    }

    @Test
    void givenInvalidIdRequest_whenDeleteById_ReturnException() {
        WalletModel mockWalletModel = GenerateWallet.build();
        WalletRequest request = GenerateWallet.buildWalletRequest();
        request.setId(mockWalletModel.getId());

        Mockito.when(walletRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementFoundException.class, () -> service.deleteById(mockWalletModel.getId()));
    }
}

