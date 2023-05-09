package com.poywallet.poywalletbackend.controller;

import com.poywallet.poywalletbackend.domain.transaction.TransactionRequest;
import com.poywallet.poywalletbackend.domain.wallet.WalletRequest;
import com.poywallet.poywalletbackend.domain.auth.CommandResponse;
import com.poywallet.poywalletbackend.web.RestApiResponse;
import com.poywallet.poywalletbackend.domain.wallet.WalletResponse;
import com.poywallet.poywalletbackend.domain.wallet.WalletService;
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

import static com.poywallet.poywalletbackend.common.Constants.CLIENT_BASE_URL;
import static com.poywallet.poywalletbackend.common.Constants.SUCCESS;

@CrossOrigin(origins = CLIENT_BASE_URL)
@RestController
@RequestMapping("/api/v1/wallet")
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
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.domain.user.RoleEnum).ROLE_USER)")
    @GetMapping("/{id}")
    public ResponseEntity<RestApiResponse<WalletResponse>> findById(@PathVariable long id) {
        final WalletResponse response = walletService.findById(id);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches a single wallet by the given iban
     *
     * @param iban
     * @return WalletResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.domain.user.RoleEnum).ROLE_USER)")
    @GetMapping("/iban/{iban}")
    public ResponseEntity<RestApiResponse<WalletResponse>> findByIban(@PathVariable String iban) {
        final WalletResponse response = walletService.findByIban(iban);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches a single wallet by the given userId
     *
     * @param userId
     * @return WalletResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.domain.user.RoleEnum).ROLE_USER)")
    @GetMapping("/user/{userId}")
    public ResponseEntity<RestApiResponse<List<WalletResponse>>> findByUserId(@PathVariable long userId) {
        final List<WalletResponse> response = walletService.findByUserId(userId);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches all wallets based on the given paging and sorting parameters
     *
     * @param pageable
     * @return List of WalletResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.domain.user.RoleEnum).ROLE_USER)")
    @GetMapping
    public ResponseEntity<RestApiResponse<Page<WalletResponse>>> findAll(Pageable pageable) {
        final Page<WalletResponse> response = walletService.findAll(pageable);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Creates a new wallet using the given request parameters
     *
     * @param request
     * @return id of the created wallet wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.domain.user.RoleEnum).ROLE_USER)")
    @PostMapping
    public ResponseEntity<RestApiResponse<CommandResponse>> create(@Valid @RequestBody WalletRequest request) {
        final CommandResponse response = walletService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Transfer funds between wallets
     *
     * @param request
     * @return id of the created transaction wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.domain.user.RoleEnum).ROLE_USER)")
    @PostMapping("/transfer")
    public ResponseEntity<RestApiResponse<CommandResponse>> transferFunds(@Valid @RequestBody TransactionRequest request) {
        final CommandResponse response = walletService.transferFunds(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Adds funds to the given wallet
     *
     * @param request
     * @return id of the created transaction wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.domain.user.RoleEnum).ROLE_USER)")
    @PostMapping("/addFunds")
    public ResponseEntity<RestApiResponse<CommandResponse>> addFunds(@Valid @RequestBody TransactionRequest request) {
        final CommandResponse response = walletService.addFunds(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Withdraw funds from the given wallet
     *
     * @param request
     * @return id of the created transaction wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.domain.user.RoleEnum).ROLE_USER)")
    @PostMapping("/withdrawFunds")
    public ResponseEntity<RestApiResponse<CommandResponse>> withdrawFunds(@Valid @RequestBody TransactionRequest request) {
        final CommandResponse response = walletService.withdrawFunds(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Updates wallet using the given request parameters
     *
     * @param request
     * @return id of the updated wallet wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.domain.user.RoleEnum).ROLE_USER)")
    @PutMapping
    public ResponseEntity<RestApiResponse<CommandResponse>> update(@Valid @RequestBody WalletRequest request) {
        final CommandResponse response = walletService.update(request);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Deletes wallet by the given id
     *
     * @param id
     * @return ResponseEntity<ApiResponse < Void>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.domain.user.RoleEnum).ROLE_USER)")
    @DeleteMapping("/{id}")
    public ResponseEntity<RestApiResponse<Void>> deleteById(@PathVariable long id) {
        walletService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
