package io.github.mrgsrylm.wallet.dto.wallet;

import io.github.mrgsrylm.wallet.model.WalletModel;
import io.github.mrgsrylm.wallet.service.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

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
    public abstract WalletModel toEntity(WalletRequest dto);

    public abstract WalletRequest toDto(WalletModel entity);

    @AfterMapping
    void setToEntityFields(@MappingTarget WalletModel entity, WalletRequest dto) {
        entity.setUser(userService.getReferenceById(dto.getUserId()));
    }
}