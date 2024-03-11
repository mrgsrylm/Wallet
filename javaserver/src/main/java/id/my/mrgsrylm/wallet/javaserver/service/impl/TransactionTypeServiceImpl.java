package id.my.mrgsrylm.wallet.javaserver.service.impl;

import id.my.mrgsrylm.wallet.javaserver.model.TransactionTypeModel;
import id.my.mrgsrylm.wallet.javaserver.repository.TransactionTypeRepository;
import id.my.mrgsrylm.wallet.javaserver.service.TransactionTypeService;
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
