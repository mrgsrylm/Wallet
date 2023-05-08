package com.poywallet.poywalletbackend.dto.response;

import com.poywallet.poywalletbackend.model.RoleEnum;
import lombok.Data;

/**
 * Data Transfer Object for Role response
 */
@Data
public class RoleResponse {
    private Long id;
    private RoleEnum type;
}
