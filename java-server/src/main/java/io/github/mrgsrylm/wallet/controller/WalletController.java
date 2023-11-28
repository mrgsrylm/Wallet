package io.github.mrgsrylm.wallet.controller;

import io.github.mrgsrylm.wallet.dto.ApiResponse;
import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionRequest;
import io.github.mrgsrylm.wallet.dto.wallet.WalletRequest;
import io.github.mrgsrylm.wallet.dto.wallet.WalletResponse;
import io.github.mrgsrylm.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

import static io.github.mrgsrylm.wallet.common.Constants.SUCCESS;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final Clock clock;
    private final WalletService walletService;

    /**
     * Fetches a single wallet by the given id
     *
     * @param id
     * @return WalletResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WalletResponse>> findById(@PathVariable long id) {
        final WalletResponse response = walletService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches a single wallet by the given iban
     *
     * @param iban
     * @return WalletResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @GetMapping("/iban/{iban}")
    public ResponseEntity<ApiResponse<WalletResponse>> findByIban(@PathVariable String iban) {
        final WalletResponse response = walletService.findByIban(iban);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches a single wallet by the given userId
     *
     * @param userId
     * @return WalletResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<WalletResponse>>> findByUserId(@PathVariable long userId) {
        final List<WalletResponse> response = walletService.findByUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches all wallets based on the given paging and sorting parameters
     *
     * @param pageable
     * @return List of WalletResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<WalletResponse>>> findAll(Pageable pageable) {
        final Page<WalletResponse> response = walletService.findAll(pageable);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Creates a new wallet using the given request parameters
     *
     * @param request
     * @return id of the created wallet wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CommandResponse>> create(@Valid @RequestBody WalletRequest request) {
        final CommandResponse response = walletService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Transfer funds between wallets
     *
     * @param request
     * @return id of the created transaction wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<CommandResponse>> transferFunds(@Valid @RequestBody TransactionRequest request) {
        final CommandResponse response = walletService.transferFunds(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Adds funds to the given wallet
     *
     * @param request
     * @return id of the created transaction wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PostMapping("/addFunds")
    public ResponseEntity<ApiResponse<CommandResponse>> addFunds(@Valid @RequestBody TransactionRequest request) {
        final CommandResponse response = walletService.addFunds(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Withdraw funds from the given wallet
     *
     * @param request
     * @return id of the created transaction wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PostMapping("/withdrawFunds")
    public ResponseEntity<ApiResponse<CommandResponse>> withdrawFunds(@Valid @RequestBody TransactionRequest request) {
        final CommandResponse response = walletService.withdrawFunds(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Updates wallet using the given request parameters
     *
     * @param request
     * @return id of the updated wallet wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PutMapping
    public ResponseEntity<ApiResponse<CommandResponse>> update(@Valid @RequestBody WalletRequest request) {
        final CommandResponse response = walletService.update(request);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Deletes wallet by the given id
     *
     * @param id
     * @return ResponseEntity<ApiResponse < Void>>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable long id) {
        walletService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
