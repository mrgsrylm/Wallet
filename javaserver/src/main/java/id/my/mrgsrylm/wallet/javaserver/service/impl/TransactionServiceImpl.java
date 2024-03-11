package id.my.mrgsrylm.wallet.javaserver.service.impl;

import id.my.mrgsrylm.wallet.javaserver.dto.CommandResponse;
import id.my.mrgsrylm.wallet.javaserver.dto.transaction.TransactionRequest;
import id.my.mrgsrylm.wallet.javaserver.dto.transaction.TransactionRequestMapper;
import id.my.mrgsrylm.wallet.javaserver.dto.transaction.TransactionResponse;
import id.my.mrgsrylm.wallet.javaserver.dto.transaction.TransactionResponseMapper;
import id.my.mrgsrylm.wallet.javaserver.exception.NoSuchElementFoundException;
import id.my.mrgsrylm.wallet.javaserver.model.TransactionModel;
import id.my.mrgsrylm.wallet.javaserver.repository.TransactionRepository;
import id.my.mrgsrylm.wallet.javaserver.repository.UserRepository;
import id.my.mrgsrylm.wallet.javaserver.repository.WalletRepository;
import id.my.mrgsrylm.wallet.javaserver.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static id.my.mrgsrylm.wallet.javaserver.common.Constants.*;

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
        final TransactionModel transactionModel = transactionRequestMapper.toEntity(request);
        transactionRepository.save(transactionModel);
        log.info(CREATED_TRANSACTION, transactionModel.getFromWallet().getIban(), transactionModel.getToWallet().getIban(), transactionModel.getAmount());
        return CommandResponse.builder().id(transactionModel.getId()).build();
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

//    @Override
//    @Transactional(readOnly = true)
//    public List<TransactionResponse> findAllByUserId(Long userId) {
//        transactionRepository.
//        final List<TransactionResponse> transactions = transactionRepository.findAllByUserId(userId).stream()
//                .map(transactionResponseMapper::toDto).toList();
//
//        if (transactions.isEmpty())
//            throw new NoSuchElementFoundException(NOT_FOUND_RECORD);
//        return transactions;
//    }

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
