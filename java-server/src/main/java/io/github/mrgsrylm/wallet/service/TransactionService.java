package io.github.mrgsrylm.wallet.service;

import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionRequest;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    CommandResponse create(TransactionRequest request);

    Page<TransactionResponse> findAll(Pageable pageable);

    // List<TransactionResponse> findAllByUserId(Long userId);

    TransactionResponse findById(long id);

    TransactionResponse findByReferenceNumber(UUID referenceNumber);
}
