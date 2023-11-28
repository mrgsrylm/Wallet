package io.github.mrgsrylm.wallet.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mrgsrylm.wallet.TestContainerConfiguration;
import io.github.mrgsrylm.wallet.fixtures.generator.UserObjectGenerator;
import io.github.mrgsrylm.wallet.model.RoleModel;
import io.github.mrgsrylm.wallet.model.UserModel;
import io.github.mrgsrylm.wallet.model.enums.RoleType;
import io.github.mrgsrylm.wallet.model.enums.TokenClaims;
import io.github.mrgsrylm.wallet.security.UserDetailsImpl;
import io.github.mrgsrylm.wallet.security.UserDetailsServiceImpl;
import io.github.mrgsrylm.wallet.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseControllerTest extends TestContainerConfiguration {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JwtUtils jwtUtils;

    @MockBean
    protected UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    protected Clock clock;

    protected UserModel mockUserModel;
    protected String mockUserToken;

    @BeforeEach
    protected void initializeAuth() {
        UserObjectGenerator generatorUser = new UserObjectGenerator();
        RoleModel USER = new RoleModel();
        USER.setId(1L);
        USER.setType(RoleType.user);
        Set<RoleModel> ROLE_Model_USER = new HashSet<>();
        ROLE_Model_USER.add(USER);
        this.mockUserModel =  generatorUser.generateUser(ROLE_Model_USER);

        final List<SimpleGrantedAuthority> authorities = mockUserModel.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getType().name()))
                .toList();
        final Map<String, Object> claims = new HashMap<>();
        claims.put(TokenClaims.ID.getValue(), mockUserModel.getId());
        claims.put(TokenClaims.USERNAME.getValue(), mockUserModel.getUsername());
        claims.put(TokenClaims.ROLES.getValue(), mockUserModel.getRoles());

        UserDetailsImpl mockUserDetails = new UserDetailsImpl(
                mockUserModel.getId(),
                mockUserModel.getUsername(),
                mockUserModel.getPassword(),
                mockUserModel.getClaims(),
                authorities
        );

        this.mockUserToken = generateMockToken(mockUserDetails);
        Mockito.when(userDetailsServiceImpl.loadUserByUsername(mockUserModel.getUsername()))
                .thenReturn(mockUserDetails);
    }

    private  String generateMockToken(UserDetailsImpl details) {
        return "Bearer ".concat(jwtUtils.generateJwtToken(details));
    }
}
