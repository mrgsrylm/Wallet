package id.my.mrgsrylm.wallet.javaserver.controller;

import id.my.mrgsrylm.wallet.javaserver.dto.ApiResponse;
import id.my.mrgsrylm.wallet.javaserver.dto.transaction.TransactionResponse;
import id.my.mrgsrylm.wallet.javaserver.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

import static id.my.mrgsrylm.wallet.javaserver.common.Constants.SUCCESS;

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
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> findById(@PathVariable long id) {
        final TransactionResponse response = transactionService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches a single transaction by the given referenceNumber
     *
     * @param referenceNumber
     * @return TransactionResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @GetMapping("/references/{referenceNumber}")
    public ResponseEntity<ApiResponse<TransactionResponse>> findByReferenceNumber(@PathVariable UUID referenceNumber) {
        final TransactionResponse response = transactionService.findByReferenceNumber(referenceNumber);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches all transaction by the given userId
     *
     * @param userId
     * @return List of TransactionResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
//    @PreAuthorize("hasRole(T(id.my.mrgsrylm.wallet.javaserver.model.enums.RoleType).user)")
//    @GetMapping("/users/{userId}")
//    public ResponseEntity<ApiResponse<Page<TransactionResponse>>> findAllByUserId(@PathVariable long userId) {
//        final Page<TransactionResponse> response = new PageImpl<>(transactionService.findAllByUserId(userId));
//        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
//    }

    /**
     * Fetches all transactions based on the given paging and sorting parameters
     *
     * @param pageable
     * @return List of TransactionResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<TransactionResponse>>> findAll(Pageable pageable) {
        final Page<TransactionResponse> response = transactionService.findAll(pageable);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }
}
