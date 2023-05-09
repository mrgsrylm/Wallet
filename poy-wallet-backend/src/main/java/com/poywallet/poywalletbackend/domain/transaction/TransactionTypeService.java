package com.poywallet.poywalletbackend.domain.transaction;

import com.poywallet.poywalletbackend.domain.transaction.TransactionType;

public interface TransactionTypeService {
    TransactionType getReferenceById(long id);
}
