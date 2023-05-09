package com.poywallet.poywalletbackend.domain.wallet;

import com.poywallet.poywalletbackend.domain.transaction.TransactionRequest;
import com.poywallet.poywalletbackend.domain.wallet.WalletRequest;
import com.poywallet.poywalletbackend.domain.auth.CommandResponse;
import com.poywallet.poywalletbackend.domain.wallet.WalletResponse;
import com.poywallet.poywalletbackend.domain.wallet.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalletService {
    Page<WalletResponse> findAll(Pageable pageable);

    List<WalletResponse> findByUserId(long userId);

    WalletResponse findById(long id);

    WalletResponse findByIban(String iban);

    Wallet getByIban(String iban);

    CommandResponse create(WalletRequest request);

    CommandResponse transferFunds(TransactionRequest request);

    CommandResponse addFunds(TransactionRequest request);

    CommandResponse withdrawFunds(TransactionRequest request);

    CommandResponse update(WalletRequest request);

    void deleteById(long id);
}
