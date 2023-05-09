package com.poywallet.poywalletbackend.mapper;

import com.poywallet.poywalletbackend.web.api.transaction.RequestTransaction;
import com.poywallet.poywalletbackend.web.api.wallet.RequestWallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper used for mapping from WalletRequest to TransactionRequest fields
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WalletTransactionRequestMapper {

    @Mapping(target = "amount", source = "balance")
    @Mapping(target = "description", constant = "Initial balance")
    @Mapping(target = "fromWalletIban", source = "iban")
    @Mapping(target = "toWalletIban", source = "iban")
    @Mapping(target = "transactionTypeId", constant = "1L")
    RequestTransaction toTransactionDto(RequestWallet entity);

    RequestWallet toWalletDto(RequestTransaction dto);
}
