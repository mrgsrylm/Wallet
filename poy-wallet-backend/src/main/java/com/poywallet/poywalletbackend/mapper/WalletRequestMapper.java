package com.poywallet.poywalletbackend.mapper;

import com.poywallet.poywalletbackend.web.api.wallet.RequestWallet;
import com.poywallet.poywalletbackend.service.UserService;
import com.poywallet.poywalletbackend.model.Wallet;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper used for mapping WalletRequest fields
 */
@Mapper(componentModel = "spring",
        uses = {UserService.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class WalletRequestMapper {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Mapping(target = "name", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getName()))")
    @Mapping(target = "iban", expression = "java(org.apache.commons.lang3.StringUtils.upperCase(dto.getIban()))")
    @Mapping(target = "user", ignore = true)
    public abstract Wallet toEntity(RequestWallet dto);

    public abstract RequestWallet toDto(Wallet entity);

    @AfterMapping
    void setToEntityFields(@MappingTarget Wallet entity, RequestWallet dto) {
        entity.setUser(userService.getReferenceById(dto.getUserId()));
    }
}
