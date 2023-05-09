package com.poywallet.poywalletbackend.web;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Builds API response in a proper format with timestamp, message and data
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
public class RestApiResponse<T> {
    private Long timestamp;
    private final String message;
    private final T data;

    public RestApiResponse(Long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
        this.data = null;
    }
}
