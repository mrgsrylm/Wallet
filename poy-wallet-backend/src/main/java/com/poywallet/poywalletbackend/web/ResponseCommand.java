package com.poywallet.poywalletbackend.web;

import lombok.Builder;

/**
 * Data Transfer Object used for returning id value of the saved entity
 */
@Builder
public record ResponseCommand(Long id) {
}
