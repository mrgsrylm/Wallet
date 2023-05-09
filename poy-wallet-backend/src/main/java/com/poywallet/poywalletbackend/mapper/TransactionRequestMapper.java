package com.poywallet.poywalletbackend.mapper;

import com.poywallet.poywalletbackend.model.Transaction;
import com.poywallet.poywalletbackend.web.api.transaction.RequestTransaction;
import com.poywallet.poywalletbackend.service.TransactionTypeService;
import com.poywallet.poywalletbackend.service.WalletService;
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
    private TransactionTypeService transactionTypeService;

    @Autowired
    public void setWalletService(@Lazy WalletService walletService) {
        this.walletService = walletService;
    }

    @Autowired
    public void setTypeService(TransactionTypeService transactionTypeService) {
        this.transactionTypeService = transactionTypeService;
    }

    // set default value of the status field as Status.SUCCESS
    @Mapping(target = "status", expression = "java(com.poywallet.poywalletbackend.model.StatusEnum.SUCCESS)")
    @Mapping(target = "referenceNumber", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "createdAt", target = "createdAt", defaultExpression = "java(java.time.Instant.now())")
    @Mapping(target = "fromWallet", ignore = true)
    @Mapping(target = "toWallet", ignore = true)
    @Mapping(target = "transactionType", ignore = true)
    public abstract Transaction toEntity(RequestTransaction dto);

    public abstract RequestTransaction toDto(Transaction entity);

    @AfterMapping
    void setToEntityFields(@MappingTarget Transaction entity, RequestTransaction dto) {
        entity.setFromWallet(walletService.getByIban(dto.getFromWalletIban()));
        entity.setToWallet(walletService.getByIban(dto.getToWalletIban()));
        entity.setTransactionType(transactionTypeService.getReferenceById(dto.getTransactionTypeId()));
    }
}
