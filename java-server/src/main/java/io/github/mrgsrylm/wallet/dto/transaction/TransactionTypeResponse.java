package io.github.mrgsrylm.wallet.dto.transaction;

import lombok.Data;

/**
 * Data Transfer Object for Type response
 */
@Data
public class TransactionTypeResponse {
    private Long id;
    private String name;
    private String description;
}
