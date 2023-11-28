package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionRequest;
import io.github.mrgsrylm.wallet.dto.wallet.*;
import io.github.mrgsrylm.wallet.exception.ElementAlreadyExistsException;
import io.github.mrgsrylm.wallet.exception.InsufficientFundsException;
import io.github.mrgsrylm.wallet.exception.NoSuchElementFoundException;
import io.github.mrgsrylm.wallet.model.WalletModel;
import io.github.mrgsrylm.wallet.repository.WalletRepository;
import io.github.mrgsrylm.wallet.service.TransactionService;
import io.github.mrgsrylm.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static io.github.mrgsrylm.wallet.common.Constants.*;

@Service
@Slf4j(topic = "WalletService")
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository repository;
    private final TransactionService transactionService;
    private final WalletResponseMapper walletResponseMapper;
    private final WalletRequestMapper walletRequestMapper;
    private final WalletTransactionRequestMapper walletTransactionRequestMapper;

    @Override
    @Transactional
    public CommandResponse create(WalletRequest request) {
        if (repository.existsByIbanIgnoreCase(request.getIban()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_WALLET_IBAN);
        if (repository.existsByUserIdAndNameIgnoreCase(request.getUserId(), request.getName()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_WALLET_NAME);

        final WalletModel walletModel = walletRequestMapper.toEntity(request);
        repository.save(walletModel);
        log.info(CREATED_WALLET, walletModel.getIban(), walletModel.getName(), walletModel.getBalance());

        // add this initial amount to the transactions
        transactionService.create(walletTransactionRequestMapper.toTransactionDto(request));

        return CommandResponse.builder().id(walletModel.getId()).build();
    }

    @Override
    @Transactional
    public CommandResponse addFunds(TransactionRequest request) {
        final WalletModel toWalletModel = getByIban(request.getToWalletIban());

        // update balance of the receiver wallet
        toWalletModel.setBalance(toWalletModel.getBalance().add(request.getAmount()));

        repository.save(toWalletModel);
        log.info(UPDATED_WALLET_BALANCE, toWalletModel.getBalance());

        final CommandResponse response = transactionService.create(request);
        return CommandResponse.builder().id(response.id()).build();
    }

    @Override
    @Transactional
    public CommandResponse transferFunds(TransactionRequest request) {
        final WalletModel toWalletModel = getByIban(request.getToWalletIban());
        final WalletModel fromWalletModel = getByIban(request.getFromWalletIban());

        // check if the balance of sender wallet has equal or higher to/than transfer amount
        if (fromWalletModel.getBalance().compareTo(request.getAmount()) < 0)
            throw new InsufficientFundsException(FUNDS_CANNOT_BELOW_ZERO);

        // update balance of the sender wallet
        fromWalletModel.setBalance(fromWalletModel.getBalance().subtract(request.getAmount()));

        // update balance of the receiver wallet
        toWalletModel.setBalance(toWalletModel.getBalance().add(request.getAmount()));

        repository.save(toWalletModel);
        log.info(UPDATED_WALLET_BALANCES, new Object[]{fromWalletModel.getBalance(), toWalletModel.getBalance()});

        final CommandResponse response = transactionService.create(request);
        return CommandResponse.builder().id(response.id()).build();
    }

    @Override
    @Transactional
    public CommandResponse withdrawFunds(TransactionRequest request) {
        final WalletModel fromWalletModel = getByIban(request.getFromWalletIban());

        // check if the balance of sender wallet has equal or higher to/than transfer amount
        if (fromWalletModel.getBalance().compareTo(request.getAmount()) < 0)
            throw new InsufficientFundsException(FUNDS_CANNOT_BELOW_ZERO);

        // update balance of the sender wallet
        fromWalletModel.setBalance(fromWalletModel.getBalance().subtract(request.getAmount()));

        repository.save(fromWalletModel);
        log.info(UPDATED_WALLET_BALANCE, new Object[]{fromWalletModel.getBalance()});

        final CommandResponse response = transactionService.create(request);
        return CommandResponse.builder().id(response.id()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WalletResponse> findAll(Pageable pageable) {
        final Page<WalletResponse> wallets = repository.findAll(pageable)
                .map(walletResponseMapper::toDto);
        if (wallets.isEmpty())
            throw new NoSuchElementException(NOT_FOUND_RECORD);
        return wallets;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WalletResponse> findByUserId(long userId) {
        return repository.findByUserId(userId).stream()
                .map(walletResponseMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public WalletResponse findById(long id) {
        return repository.findById(id)
                .map(walletResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_WALLET));
    }

    @Override
    @Transactional(readOnly = true)
    public WalletResponse findByIban(String iban) {
        return repository.findByIban(iban)
                .map(walletResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_WALLET));
    }

    @Override
    @Transactional(readOnly = true)
    public WalletModel getByIban(String iban) {
        return repository.findByIban(iban)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_WALLET));
    }

    @Override
    public CommandResponse update(WalletRequest request) {
        final WalletModel foundWalletModel = repository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_WALLET));

        // check if the iban is changed and new iban is already exists
        if (!request.getIban().equalsIgnoreCase(foundWalletModel.getIban()) &&
                repository.existsByIbanIgnoreCase(request.getIban()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_WALLET_IBAN);

        // check if the name is changed and new name is already exists in user's wallets
        if (!request.getName().equalsIgnoreCase(foundWalletModel.getName()) &&
                repository.existsByUserIdAndNameIgnoreCase(request.getUserId(), request.getName()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_WALLET_NAME);

        final WalletModel walletModel = walletRequestMapper.toEntity(request);
        repository.save(walletModel);
        log.info(UPDATED_WALLET, new Object[]{walletModel.getIban(), walletModel.getName(), walletModel.getBalance()});
        return CommandResponse.builder().id(walletModel.getId()).build();
    }

    @Override
    public void deleteById(long id) {
        final WalletModel walletModel = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_WALLET));
        repository.delete(walletModel);
        log.info(DELETED_WALLET, walletModel.getIban(), walletModel.getName(), walletModel.getBalance());
    }
}
