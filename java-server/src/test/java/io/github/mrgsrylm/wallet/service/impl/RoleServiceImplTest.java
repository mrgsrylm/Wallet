package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.base.BaseServiceTest;
import io.github.mrgsrylm.wallet.fixtures.GenerateRole;
import io.github.mrgsrylm.wallet.model.Role;
import io.github.mrgsrylm.wallet.model.enums.RoleType;
import io.github.mrgsrylm.wallet.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;

public class RoleServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void givenRoleTypes_whenGetReferenceByTypeIs_ReturnSuccess() {
        Set<RoleType> mockRoleTypes = GenerateRole.buildAsSetRoleType();
        List<Role> mockRole = GenerateRole.buildAsList();

        Mockito.when(roleRepository.getReferenceByTypeIsIn(Mockito.anySet())).thenReturn(mockRole);

        List<Role> result = roleService.getReferenceByTypeIsIn(mockRoleTypes);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, mockRole);
        Mockito.verify(roleRepository, Mockito.times(1)).getReferenceByTypeIsIn(Mockito.anySet());

    }

    @Test
    void givenEmpty_whenFindAll_ReturnSuccessListRole() {
        List<Role> mockRole = GenerateRole.buildAsList();

        Mockito.when(roleRepository.findAll()).thenReturn(mockRole);

        List<Role> result = roleService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, mockRole);
        Mockito.verify(roleRepository, Mockito.times(1)).findAll();

    }
}
