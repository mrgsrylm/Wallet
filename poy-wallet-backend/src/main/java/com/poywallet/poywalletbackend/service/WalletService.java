package com.poywallet.poywalletbackend.service;

import com.poywallet.poywalletbackend.web.api.transaction.RequestTransaction;
import com.poywallet.poywalletbackend.web.api.wallet.RequestWallet;
import com.poywallet.poywalletbackend.web.api.wallet.ResponseWallet;
import com.poywallet.poywalletbackend.model.Wallet;
import com.poywallet.poywalletbackend.web.ResponseCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalletService {
    Page<ResponseWallet> findAll(Pageable pageable);

    List<ResponseWallet> findByUserId(long userId);

    ResponseWallet findById(long id);

    ResponseWallet findByIban(String iban);

    Wallet getByIban(String iban);

    ResponseCommand create(RequestWallet request);

    ResponseCommand transferFunds(RequestTransaction request);

    ResponseCommand addFunds(RequestTransaction request);

    ResponseCommand withdrawFunds(RequestTransaction request);

    ResponseCommand update(RequestWallet request);

    void deleteById(long id);
}
