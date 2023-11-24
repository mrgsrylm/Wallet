package io.github.mrgsrylm.wallet.dto.wallet;

import io.github.mrgsrylm.wallet.dto.user.UserResponse;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Wallet response
 */
@Data
public class WalletResponse {

    private Long id;
    private String iban;
    private String name;
    private BigDecimal balance;
    private UserResponse user;
}

