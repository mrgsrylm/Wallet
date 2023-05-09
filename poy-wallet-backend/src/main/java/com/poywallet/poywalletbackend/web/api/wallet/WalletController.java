package com.poywallet.poywalletbackend.web.api.wallet;

import com.poywallet.poywalletbackend.web.api.transaction.RequestTransaction;
import com.poywallet.poywalletbackend.web.ResponseCommand;
import com.poywallet.poywalletbackend.web.RestApiResponse;
import com.poywallet.poywalletbackend.service.WalletService;
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
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @GetMapping("/{id}")
    public ResponseEntity<RestApiResponse<ResponseWallet>> findById(@PathVariable long id) {
        final ResponseWallet response = walletService.findById(id);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches a single wallet by the given iban
     *
     * @param iban
     * @return WalletResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @GetMapping("/iban/{iban}")
    public ResponseEntity<RestApiResponse<ResponseWallet>> findByIban(@PathVariable String iban) {
        final ResponseWallet response = walletService.findByIban(iban);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches a single wallet by the given userId
     *
     * @param userId
     * @return WalletResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @GetMapping("/users/{userId}")
    public ResponseEntity<RestApiResponse<List<ResponseWallet>>> findByUserId(@PathVariable long userId) {
        final List<ResponseWallet> response = walletService.findByUserId(userId);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches all wallets based on the given paging and sorting parameters
     *
     * @param pageable
     * @return List of WalletResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @GetMapping
    public ResponseEntity<RestApiResponse<Page<ResponseWallet>>> findAll(Pageable pageable) {
        final Page<ResponseWallet> response = walletService.findAll(pageable);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Creates a new wallet using the given request parameters
     *
     * @param request
     * @return id of the created wallet wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @PostMapping
    public ResponseEntity<RestApiResponse<ResponseCommand>> create(@Valid @RequestBody RequestWallet request) {
        final ResponseCommand response = walletService.create(request);
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
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @PostMapping("/transfer")
    public ResponseEntity<RestApiResponse<ResponseCommand>> transferFunds(@Valid @RequestBody RequestTransaction request) {
        final ResponseCommand response = walletService.transferFunds(request);
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
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @PostMapping("/addFunds")
    public ResponseEntity<RestApiResponse<ResponseCommand>> addFunds(@Valid @RequestBody RequestTransaction request) {
        final ResponseCommand response = walletService.addFunds(request);
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
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @PostMapping("/withdrawFunds")
    public ResponseEntity<RestApiResponse<ResponseCommand>> withdrawFunds(@Valid @RequestBody RequestTransaction request) {
        final ResponseCommand response = walletService.withdrawFunds(request);
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
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @PutMapping
    public ResponseEntity<RestApiResponse<ResponseCommand>> update(@Valid @RequestBody RequestWallet request) {
        final ResponseCommand response = walletService.update(request);
        return ResponseEntity.ok(new RestApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Deletes wallet by the given id
     *
     * @param id
     * @return ResponseEntity<ApiResponse < Void>>
     */
    @PreAuthorize("hasRole(T(com.poywallet.poywalletbackend.model.RoleEnum).ROLE_USER)")
    @DeleteMapping("/{id}")
    public ResponseEntity<RestApiResponse<Void>> deleteById(@PathVariable long id) {
        walletService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
