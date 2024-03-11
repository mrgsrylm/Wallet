package id.my.mrgsrylm.wallet.javaserver.dto.wallet;

import id.my.mrgsrylm.wallet.javaserver.dto.user.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Wallet response
 */
@Data
@Builder
public class WalletResponse {
    private Long id;
    private String iban;
    private String name;
    private BigDecimal balance;
    private UserResponse user;
}

