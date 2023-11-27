package io.github.mrgsrylm.wallet.service;

import io.github.mrgsrylm.wallet.model.TransactionType;

public interface TransactionTypeService {
    TransactionType getReferenceById(long id);
}
