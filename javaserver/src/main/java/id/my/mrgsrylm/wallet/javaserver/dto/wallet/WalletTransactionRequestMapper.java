package id.my.mrgsrylm.wallet.javaserver.dto.wallet;

import id.my.mrgsrylm.wallet.javaserver.dto.transaction.TransactionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WalletTransactionRequestMapper {
    @Mapping(target = "amount", source = "balance")
    @Mapping(target = "description", constant = "Initial balance")
    @Mapping(target = "fromWalletIban", source = "iban")
    @Mapping(target = "toWalletIban", source = "iban")
    @Mapping(target = "transactionTypeId", constant = "1L")
    TransactionRequest toTransactionDto(WalletRequest entity);

    WalletRequest toWalletDto(TransactionRequest dto);
}
