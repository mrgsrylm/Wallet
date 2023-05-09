package com.poywallet.poywalletbackend.domain.transaction;

import com.poywallet.poywalletbackend.domain.transaction.TransactionRequest;
import com.poywallet.poywalletbackend.domain.auth.CommandResponse;
import com.poywallet.poywalletbackend.domain.transaction.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    Page<TransactionResponse> findAll(Pageable pageable);

    List<TransactionResponse> findAllByUserId(Long userId);

    TransactionResponse findById(long id);

    TransactionResponse findByReferenceNumber(UUID referenceNumber);

    CommandResponse create(TransactionRequest request);
}
