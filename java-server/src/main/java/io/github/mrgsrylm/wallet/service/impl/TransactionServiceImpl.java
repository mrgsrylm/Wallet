package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionRequest;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionRequestMapper;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionResponse;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionResponseMapper;
import io.github.mrgsrylm.wallet.exception.NoSuchElementFoundException;
import io.github.mrgsrylm.wallet.model.Transaction;
import io.github.mrgsrylm.wallet.repository.TransactionRepository;
import io.github.mrgsrylm.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static io.github.mrgsrylm.wallet.common.Constants.*;

@Service
@Slf4j(topic = "TransactionService")
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionRequestMapper transactionRequestMapper;
    private final TransactionResponseMapper transactionResponseMapper;

    @Override
    @Transactional
    public CommandResponse create(TransactionRequest request) {
        final Transaction transaction = transactionRequestMapper.toEntity(request);
        transactionRepository.save(transaction);
        log.info(CREATED_TRANSACTION, transaction.getFromWallet().getIban(), transaction.getToWallet().getIban(), transaction.getAmount());
        return CommandResponse.builder().id(transaction.getId()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse> findAll(Pageable pageable) {
        final Page<TransactionResponse> transactions = transactionRepository.findAll(pageable)
                .map(transactionResponseMapper::toDto);
        if (transactions.isEmpty())
            throw new NoSuchElementFoundException(NOT_FOUND_RECORD);
        return transactions;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> findAllByUserId(Long userId) {
        final List<TransactionResponse> transactions = transactionRepository.findAllByUserId(userId).stream()
                .map(transactionResponseMapper::toDto).toList();

        if (transactions.isEmpty())
            throw new NoSuchElementFoundException(NOT_FOUND_RECORD);
        return transactions;
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse findById(long id) {
        return transactionRepository.findById(id)
                .map(transactionResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_TRANSACTION));
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse findByReferenceNumber(UUID referenceNumber) {
        return transactionRepository.findByReferenceNumber(referenceNumber)
                .map(transactionResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_TRANSACTION));
    }
}
