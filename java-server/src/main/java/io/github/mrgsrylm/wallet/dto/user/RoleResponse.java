package io.github.mrgsrylm.wallet.dto.user;

import io.github.mrgsrylm.wallet.model.enums.RoleType;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for Role response
 */
@Data
@Builder
public class RoleResponse {
    private Long id;
    private RoleType type;
}
