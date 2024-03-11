package id.my.mrgsrylm.wallet.javaserver.dto.transaction;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for Type response
 */
@Data
@Builder
public class TransactionTypeResponse {
    private Long id;
    private String name;
    private String description;
}
