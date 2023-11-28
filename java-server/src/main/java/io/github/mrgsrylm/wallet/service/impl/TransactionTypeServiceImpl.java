package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.model.TransactionTypeModel;
import io.github.mrgsrylm.wallet.repository.TransactionTypeRepository;
import io.github.mrgsrylm.wallet.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "TransactionTypeService")
@RequiredArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService {
    private final TransactionTypeRepository repository;

    @Override
    public TransactionTypeModel getReferenceById(long id) {
        return repository.getReferenceById(id);
    }
}
