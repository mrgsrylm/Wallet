package id.my.mrgsrylm.wallet.javaserver.service.impl;

import id.my.mrgsrylm.wallet.javaserver.base.BaseServiceTest;
import id.my.mrgsrylm.wallet.javaserver.fixtures.GenerateTransactionType;
import id.my.mrgsrylm.wallet.javaserver.model.TransactionTypeModel;
import id.my.mrgsrylm.wallet.javaserver.repository.TransactionTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class TransactionTypeServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private TransactionTypeServiceImpl service;

    @Mock
    private TransactionTypeRepository repository;

    @Test
    void givenId_whenGetReferenceById_ReturnSuccessUser() {
        TransactionTypeModel mockTType = GenerateTransactionType.build();

        Mockito.when(repository.getReferenceById(Mockito.anyLong())).thenReturn(mockTType);

        TransactionTypeModel result = service.getReferenceById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, mockTType);
        Mockito.verify(repository, Mockito.times(1)).getReferenceById(Mockito.anyLong());

    }
}
