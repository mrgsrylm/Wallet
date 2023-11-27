package io.github.mrgsrylm.wallet.dto;

import lombok.Builder;

/**
 * Data Transfer Object used for returning id value of the saved entity
 */
@Builder
public record CommandResponse(Long id) {
}
