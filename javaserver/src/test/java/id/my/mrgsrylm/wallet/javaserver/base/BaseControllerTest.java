package id.my.mrgsrylm.wallet.javaserver.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.mrgsrylm.wallet.javaserver.TestContainerConfiguration;
import id.my.mrgsrylm.wallet.javaserver.fixtures.generator.UserObjectGenerator;
import id.my.mrgsrylm.wallet.javaserver.model.UserModel;
import id.my.mrgsrylm.wallet.javaserver.model.enums.RoleType;
import id.my.mrgsrylm.wallet.javaserver.model.enums.TokenClaims;
import id.my.mrgsrylm.wallet.javaserver.security.UserDetailsImpl;
import id.my.mrgsrylm.wallet.javaserver.security.UserDetailsServiceImpl;
import id.my.mrgsrylm.wallet.javaserver.security.jwt.JwtUtils;
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
        this.mockUserModel =  generatorUser.generateUser(RoleType.USER);
        UserDetailsImpl mockUserDetails = new UserDetailsImpl(mockUserModel);
        this.mockUserToken = generateMockToken(mockUserDetails);
        Mockito.when(userDetailsServiceImpl.loadUserByUsername(mockUserModel.getUsername()))
                .thenReturn(mockUserDetails);
    }

    private  String generateMockToken(UserDetailsImpl details) {
        return "Bearer ".concat(jwtUtils.generateJwtToken(details));
    }
}
