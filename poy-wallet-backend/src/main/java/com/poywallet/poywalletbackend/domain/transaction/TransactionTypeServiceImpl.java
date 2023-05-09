package com.poywallet.poywalletbackend.domain.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service used for Type related operations
 */
@Slf4j(topic = "TransactionTypeService")
@Service
@RequiredArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService {
    private final TransactionTypeRepository transactionTypeRepository;

    /**
     * Fetches a single type reference (entity) by the given id
     *
     * @param id
     * @return Type
     */
    @Override
    public TransactionType getReferenceById(long id) {
        return transactionTypeRepository.getReferenceById(id);
    }
}
