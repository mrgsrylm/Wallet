package com.poywallet.poywalletbackend.service;

import com.poywallet.poywalletbackend.web.api.transaction.RequestTransaction;
import com.poywallet.poywalletbackend.web.api.transaction.ResponseTransaction;
import com.poywallet.poywalletbackend.web.ResponseCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    Page<ResponseTransaction> findAll(Pageable pageable);

    List<ResponseTransaction> findAllByUserId(Long userId);

    ResponseTransaction findById(long id);

    ResponseTransaction findByReferenceNumber(UUID referenceNumber);

    ResponseCommand create(RequestTransaction request);
}
