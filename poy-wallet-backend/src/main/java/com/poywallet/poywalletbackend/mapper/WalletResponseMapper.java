package com.poywallet.poywalletbackend.mapper;

import com.poywallet.poywalletbackend.web.api.user.ResponseUser;
import com.poywallet.poywalletbackend.web.api.wallet.ResponseWallet;
import com.poywallet.poywalletbackend.model.User;
import com.poywallet.poywalletbackend.model.Wallet;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.text.MessageFormat;

/**
 * Mapper used for mapping WalletResponse fields
 */
@Mapper(componentModel = "spring")
public interface WalletResponseMapper {
    Wallet toEntity(ResponseWallet dto);

    ResponseWallet toDto(Wallet entity);

    @AfterMapping
    default void setFullName(@MappingTarget ResponseUser dto, User entity) {
        dto.setFullName(MessageFormat.format("{0} {1}", entity.getFirstName(), entity.getLastName()));
    }
}
