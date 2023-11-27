package io.github.mrgsrylm.wallet.service;

import io.github.mrgsrylm.wallet.dto.CommandResponse;
import io.github.mrgsrylm.wallet.dto.transaction.TransactionRequest;
import io.github.mrgsrylm.wallet.dto.wallet.WalletRequest;
import io.github.mrgsrylm.wallet.dto.wallet.WalletResponse;
import io.github.mrgsrylm.wallet.model.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalletService {
    CommandResponse create(WalletRequest request);

    CommandResponse addFunds(TransactionRequest request);

    CommandResponse transferFunds(TransactionRequest request);

    CommandResponse withdrawFunds(TransactionRequest request);

    Page<WalletResponse> findAll(Pageable pageable);

    List<WalletResponse> findByUserId(long userId);

    WalletResponse findById(long id);

    WalletResponse findByIban(String iban);

    Wallet getByIban(String iban);

    CommandResponse update(WalletRequest request);

    void deleteById(long id);
}
