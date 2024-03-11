package id.my.mrgsrylm.wallet.javaserver.service.impl;

import id.my.mrgsrylm.wallet.javaserver.base.BaseServiceTest;
import id.my.mrgsrylm.wallet.javaserver.fixtures.GenerateUser;
import id.my.mrgsrylm.wallet.javaserver.model.UserModel;
import id.my.mrgsrylm.wallet.javaserver.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class UserServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository userRepository;

    @Test
    void givenId_whenGetReferenceById_ReturnSuccessUser() {
        UserModel mockUserModel = GenerateUser.buildUser();

        Mockito.when(userRepository.getReferenceById(Mockito.anyLong())).thenReturn(mockUserModel);

        UserModel result = service.getReferenceById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, mockUserModel);
        Mockito.verify(userRepository, Mockito.times(1)).getReferenceById(Mockito.anyLong());

    }
}
