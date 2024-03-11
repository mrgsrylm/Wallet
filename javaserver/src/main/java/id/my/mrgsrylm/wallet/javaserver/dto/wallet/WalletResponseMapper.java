package id.my.mrgsrylm.wallet.javaserver.dto.wallet;

import id.my.mrgsrylm.wallet.javaserver.dto.user.UserResponse;
import id.my.mrgsrylm.wallet.javaserver.model.UserModel;
import id.my.mrgsrylm.wallet.javaserver.model.WalletModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.text.MessageFormat;

@Mapper(componentModel = "spring")
public interface WalletResponseMapper {
    WalletModel toEntity(WalletResponse dto);

    WalletResponse toDto(WalletModel entity);

    @AfterMapping
    default void setFullName(@MappingTarget UserResponse dto, UserModel entity) {
        dto.setFullName(MessageFormat.format("{0} {1}", entity.getFirstName(), entity.getLastName()));
    }
}
