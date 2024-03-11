package id.my.mrgsrylm.wallet.javaserver.service;

import id.my.mrgsrylm.wallet.javaserver.dto.CommandResponse;
import id.my.mrgsrylm.wallet.javaserver.dto.transaction.TransactionRequest;
import id.my.mrgsrylm.wallet.javaserver.dto.transaction.TransactionResponse;
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
