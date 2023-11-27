package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.base.BaseServiceTest;
import io.github.mrgsrylm.wallet.fixtures.GenerateUser;
import io.github.mrgsrylm.wallet.model.User;
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
        User mockUser = GenerateUser.build();

        Mockito.when(userRepository.getReferenceById(Mockito.anyLong())).thenReturn(mockUser);

        User result = service.getReferenceById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, mockUser);
        Mockito.verify(userRepository, Mockito.times(1)).getReferenceById(Mockito.anyLong());

    }
}
