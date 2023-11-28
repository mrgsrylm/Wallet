package io.github.mrgsrylm.wallet.dto.transaction;

import io.github.mrgsrylm.wallet.model.TransactionModel;
import io.github.mrgsrylm.wallet.service.TransactionTypeService;
import io.github.mrgsrylm.wallet.service.WalletService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * Mapper used for mapping TransactionRequest fields
 */
@Mapper(componentModel = "spring",
        uses = {WalletService.class, TransactionTypeService.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class TransactionRequestMapper {
    private WalletService walletService;
    private TransactionTypeService typeService;

    @Autowired
    public void setWalletService(@Lazy WalletService walletService) {
        this.walletService = walletService;
    }

    @Autowired
    public void setTypeService(TransactionTypeService typeService) {
        this.typeService = typeService;
    }

    // set default value of the status field as Status.SUCCESS
    @Mapping(target = "status", expression = "java(io.github.mrgsrylm.wallet.model.enums.TransactionStatus.SUCCESS)")
    @Mapping(target = "referenceNumber", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "createdAt", target = "createdAt", defaultExpression = "java(java.time.Instant.now())")
    @Mapping(target = "fromWallet", ignore = true)
    @Mapping(target = "toWallet", ignore = true)
    @Mapping(target = "transactionType", ignore = true)
    public abstract TransactionModel toEntity(TransactionRequest dto);

    public abstract TransactionRequest toDto(TransactionModel entity);

    @AfterMapping
    void setToEntityFields(@MappingTarget TransactionModel entity, TransactionRequest dto) {
        entity.setFromWallet(walletService.getByIban(dto.getFromWalletIban()));
        entity.setToWallet(walletService.getByIban(dto.getToWalletIban()));
        entity.setTransactionType(typeService.getReferenceById(dto.getTransactionTypeId()));
    }
}
