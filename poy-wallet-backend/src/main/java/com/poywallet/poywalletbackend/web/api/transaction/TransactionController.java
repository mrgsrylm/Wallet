package com.poywallet.poywalletbackend.web.api.transaction;

import com.poywallet.poywalletbackend.web.RestApiResponse;
import com.poywallet.poywalletbackend.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

import static com.poywallet.poywalletbackend.common.Constants.*;

@CrossOrigin(origins = CLIENT_BASE_URL)
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final Clock clock;
    private final TransactionService transactionService;

    /**
     * Fetches a single transaction by the given id
     *
     * @param id
     * @return TransactionResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @GetMapping("/{id}")
    public ResponseEntity<RestApiResponse<ResponseTransaction>> findById(@PathVariable long id) {
        final ResponseTransaction response = transactionService.findById(id);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches a single transaction by the given referenceNumber
     *
     * @param referenceNumber
     * @return TransactionResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @GetMapping("/references/{referenceNumber}")
    public ResponseEntity<RestApiResponse<ResponseTransaction>> findByReferenceNumber(@PathVariable UUID referenceNumber) {
        final ResponseTransaction response = transactionService.findByReferenceNumber(referenceNumber);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches all transaction by the given userId
     *
     * @param userId
     * @return List of TransactionResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @GetMapping("/users/{userId}")
    public ResponseEntity<RestApiResponse<Page<ResponseTransaction>>> findAllByUserId(@PathVariable long userId) {
        final Page<ResponseTransaction> response = new PageImpl<>(transactionService.findAllByUserId(userId));
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches all transactions based on the given paging and sorting parameters
     *
     * @param pageable
     * @return List of TransactionResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @GetMapping
    public ResponseEntity<RestApiResponse<Page<ResponseTransaction>>> findAll(Pageable pageable) {
        final Page<ResponseTransaction> response = transactionService.findAll(pageable);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }
}
