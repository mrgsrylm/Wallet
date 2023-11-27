package io.github.mrgsrylm.wallet.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mrgsrylm.wallet.TestContainerConfiguration;
import io.github.mrgsrylm.wallet.fixtures.GenerateUser;
import io.github.mrgsrylm.wallet.fixtures.generator.UserObjectGenerator;
import io.github.mrgsrylm.wallet.model.Role;
import io.github.mrgsrylm.wallet.model.User;
import io.github.mrgsrylm.wallet.model.enums.RoleType;
import io.github.mrgsrylm.wallet.security.UserDetailsImpl;
import io.github.mrgsrylm.wallet.security.UserDetailsServiceImpl;
import io.github.mrgsrylm.wallet.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.Clock;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseControllerTest extends TestContainerConfiguration {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    protected JwtUtils jwtUtils;

    protected User mockUser;
    protected String mockUserToken;
    protected User mockAdmin;
    protected String mockAdminToken;

    @BeforeEach
    protected void initializeAuth() {
        Role USER = Role.builder().id(1L).type(RoleType.ROLE_USER).build();
        Set<Role> ROLE_USER = new HashSet<>();
        ROLE_USER.add(USER);

        UserObjectGenerator generatorUser = new UserObjectGenerator();
        this.mockUser =  generatorUser.generateUser(ROLE_USER);

        final UserDetailsImpl mockUserDetails = new UserDetailsImpl(mockUser);
        this.mockUserToken = generateMockToken(mockUserDetails);
        Mockito.when(userDetailsServiceImpl.loadUserByUsername(mockUser.getUsername()))
                .thenReturn(mockUserDetails);
    }

    private  String generateMockToken(UserDetailsImpl details) {
        return "Bearer ".concat(jwtUtils.generateJwtToken(details));
    }
}
