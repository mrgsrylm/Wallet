package com.poywallet.poywalletbackend.service.impl;

import com.poywallet.poywalletbackend.web.api.transaction.RequestTransaction;
import com.poywallet.poywalletbackend.web.api.wallet.RequestWallet;
import com.poywallet.poywalletbackend.web.api.wallet.ResponseWallet;
import com.poywallet.poywalletbackend.mapper.WalletTransactionRequestMapper;
import com.poywallet.poywalletbackend.mapper.WalletRequestMapper;
import com.poywallet.poywalletbackend.mapper.WalletResponseMapper;
import com.poywallet.poywalletbackend.model.Wallet;
import com.poywallet.poywalletbackend.repository.WalletRepository;
import com.poywallet.poywalletbackend.service.WalletService;
import com.poywallet.poywalletbackend.web.ResponseCommand;
import com.poywallet.poywalletbackend.exception.ElementAlreadyExistsException;
import com.poywallet.poywalletbackend.exception.InsufficientFundsException;
import com.poywallet.poywalletbackend.exception.NoSuchElementFoundException;
import com.poywallet.poywalletbackend.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.poywallet.poywalletbackend.common.Constants.*;

/**
 * Service used for Wallet related operations
 */
@Slf4j(topic = "WalletService")
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final TransactionService transactionService;
    private final WalletRequestMapper walletRequestMapper;
    private final WalletResponseMapper walletResponseMapper;
    private final WalletTransactionRequestMapper walletTransactionRequestMapper;

    /**
     * Fetches all wallets based on the given paging and sorting parameters
     *
     * @param pageable
     * @return List of WalletResponse
     */
    @Transactional(readOnly = true)
    @Override
    public Page<ResponseWallet> findAll(Pageable pageable) {
        final Page<ResponseWallet> wallets = walletRepository.findAll(pageable)
                .map(walletResponseMapper::toDto);
        if (wallets.isEmpty())
            throw new NoSuchElementFoundException(NOT_FOUND_RECORD);
        return wallets;
    }

    /**
     * Fetches a single wallet by the given userId
     *
     * @param userId
     * @return WalletResponse
     */
    @Transactional(readOnly = true)
    @Override
    public List<ResponseWallet> findByUserId(long userId) {
        return walletRepository.findByUserId(userId).stream()
                .map(walletResponseMapper::toDto)
                .toList();
    }

    /**
     * Fetches a single wallet by the given id
     *
     * @param id
     * @return WalletResponse
     */
    @Transactional(readOnly = true)
    @Override
    public ResponseWallet findById(long id) {
        return walletRepository.findById(id)
                .map(walletResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_WALLET));
    }

    /**
     * Fetches a single wallet by the given iban
     *
     * @param iban
     * @return WalletResponse
     */
    @Transactional(readOnly = true)
    @Override
    public ResponseWallet findByIban(String iban) {
        return walletRepository.findByIban(iban)
                .map(walletResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_WALLET));
    }

    /**
     * Fetches a single wallet reference (entity) by the given id
     *
     * @param iban
     * @return Wallet
     */
    @Override
    public Wallet getByIban(String iban) {
        return walletRepository.findByIban(iban)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_WALLET));
    }

    /**
     * Creates a new wallet using the given request parameters
     *
     * @param request
     * @return id of the created wallet
     */
    @Transactional
    @Override
    public ResponseCommand create(RequestWallet request) {
        if (walletRepository.existsByIbanIgnoreCase(request.getIban()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_WALLET_IBAN);
        if (walletRepository.existsByUserIdAndNameIgnoreCase(request.getUserId(), request.getName()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_WALLET_NAME);

        final Wallet wallet = walletRequestMapper.toEntity(request);
        walletRepository.save(wallet);
        log.info(CREATED_WALLET, wallet.getIban(), wallet.getName(), wallet.getBalance());

        // add this initial amount to the transactions
        transactionService.create(walletTransactionRequestMapper.toTransactionDto(request));

        return ResponseCommand.builder().id(wallet.getId()).build();
    }

    /**
     * Transfer funds between wallets
     *
     * @param request
     * @return id of the transaction
     */
    @Transactional
    @Override
    public ResponseCommand transferFunds(RequestTransaction request) {
        final Wallet toWallet = getByIban(request.getToWalletIban());
        final Wallet fromWallet = getByIban(request.getFromWalletIban());

        // check if the balance of sender wallet has equal or higher to/than transfer amount
        if (fromWallet.getBalance().compareTo(request.getAmount()) < 0)
            throw new InsufficientFundsException(FUNDS_CANNOT_BELOW_ZERO);

        // update balance of the sender wallet
        fromWallet.setBalance(fromWallet.getBalance().subtract(request.getAmount()));

        // update balance of the receiver wallet
        toWallet.setBalance(toWallet.getBalance().add(request.getAmount()));

        walletRepository.save(toWallet);
        log.info(UPDATED_WALLET_BALANCES, fromWallet.getBalance(), toWallet.getBalance());

        final ResponseCommand response = transactionService.create(request);
        return ResponseCommand.builder().id(response.id()).build();
    }

    /**
     * Adds funds to the given wallet
     *
     * @param request
     * @return id of the transaction
     */
    @Transactional
    @Override
    public ResponseCommand addFunds(RequestTransaction request) {
        final Wallet toWallet = getByIban(request.getToWalletIban());

        // update balance of the receiver wallet
        toWallet.setBalance(toWallet.getBalance().add(request.getAmount()));

        walletRepository.save(toWallet);
        log.info(UPDATED_WALLET_BALANCE, toWallet.getBalance());

        final ResponseCommand response = transactionService.create(request);
        return ResponseCommand.builder().id(response.id()).build();
    }

    /**
     * Withdraw funds from the given wallet
     *
     * @param request
     * @return id of the transaction
     */
    @Transactional
    @Override
    public ResponseCommand withdrawFunds(RequestTransaction request) {
        final Wallet fromWallet = getByIban(request.getFromWalletIban());

        // check if the balance of sender wallet has equal or higher to/than transfer amount
        if (fromWallet.getBalance().compareTo(request.getAmount()) < 0)
            throw new InsufficientFundsException(FUNDS_CANNOT_BELOW_ZERO);

        // update balance of the sender wallet
        fromWallet.setBalance(fromWallet.getBalance().subtract(request.getAmount()));

        walletRepository.save(fromWallet);
        log.info(UPDATED_WALLET_BALANCE, fromWallet.getBalance());

        final ResponseCommand response = transactionService.create(request);
        return ResponseCommand.builder().id(response.id()).build();
    }

    /**
     * Updates wallet using the given request parameters
     *
     * @param request
     * @return id of the updated wallet
     */
    @Override
    public ResponseCommand update(RequestWallet request) {
        final Wallet foundWallet = walletRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_WALLET));

        // check if the iban is changed and new iban is already exists
        if (!request.getIban().equalsIgnoreCase(foundWallet.getIban()) &&
                walletRepository.existsByIbanIgnoreCase(request.getIban()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_WALLET_IBAN);

        // check if the name is changed and new name is already exists in user's wallets
        if (!request.getName().equalsIgnoreCase(foundWallet.getName()) &&
                walletRepository.existsByUserIdAndNameIgnoreCase(request.getUserId(), request.getName()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_WALLET_NAME);

        final Wallet wallet = walletRequestMapper.toEntity(request);
        walletRepository.save(wallet);
        log.info(UPDATED_WALLET, wallet.getIban(), wallet.getName(), wallet.getBalance());
        return ResponseCommand.builder().id(wallet.getId()).build();
    }

    /**
     * Deletes wallet by the given id
     *
     * @param id
     */
    @Override
    public void deleteById(long id) {
        final Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_WALLET));
        walletRepository.delete(wallet);
        log.info(DELETED_WALLET, new Object[]{wallet.getIban(), wallet.getName(), wallet.getBalance()});
    }
}
