package io.github.mrgsrylm.wallet.dto.auth;

import io.github.mrgsrylm.wallet.model.UserModel;
import io.github.mrgsrylm.wallet.model.enums.RoleType;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;

/**
 * Mapper used for mapping SignupRequest fields
 */
@Mapper(componentModel = "spring",
        uses = {PasswordEncoder.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class SignUpRequestMapper {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Mapping(target = "firstName", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getFirstName()))")
    @Mapping(target = "lastName", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getLastName()))")
    @Mapping(target = "username", expression = "java(dto.getUsername().trim().toLowerCase())")
    @Mapping(target = "email", expression = "java(dto.getEmail().trim().toLowerCase())")
    @Mapping(target = "password", expression = "java(dto.getPassword())")
    public abstract UserModel toEntity(SignUpRequest dto);

    @AfterMapping
    void setToEntityFields(@MappingTarget UserModel entity, SignUpRequest dto) {
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
    }
}
