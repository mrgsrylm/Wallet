package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.base.BaseServiceTest;
import io.github.mrgsrylm.wallet.fixtures.GenerateUser;
import io.github.mrgsrylm.wallet.model.UserModel;
import io.github.mrgsrylm.wallet.repository.UserRepository;
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
