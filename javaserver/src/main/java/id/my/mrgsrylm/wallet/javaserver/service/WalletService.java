package id.my.mrgsrylm.wallet.javaserver.service;

import id.my.mrgsrylm.wallet.javaserver.dto.CommandResponse;
import id.my.mrgsrylm.wallet.javaserver.dto.transaction.TransactionRequest;
import id.my.mrgsrylm.wallet.javaserver.dto.wallet.WalletRequest;
import id.my.mrgsrylm.wallet.javaserver.dto.wallet.WalletResponse;
import id.my.mrgsrylm.wallet.javaserver.model.WalletModel;
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

    WalletModel getByIban(String iban);

    CommandResponse update(WalletRequest request);

    void deleteById(long id);
}
