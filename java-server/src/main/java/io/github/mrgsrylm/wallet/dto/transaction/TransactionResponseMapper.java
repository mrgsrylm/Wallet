package io.github.mrgsrylm.wallet.dto.transaction;

import io.github.mrgsrylm.wallet.model.TransactionModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static io.github.mrgsrylm.wallet.common.Constants.DATE_TIME_FORMAT;

/**
 * Mapper used for mapping TransactionResponse fields
 */
@Mapper(componentModel = "spring")
public interface TransactionResponseMapper {
    TransactionModel toEntity(TransactionResponse dto);

    @Mapping(target = "createdAt", ignore = true)
    TransactionResponse toDto(TransactionModel entity);

    @AfterMapping
    default void formatCreatedAt(@MappingTarget TransactionResponse dto, TransactionModel entity) {
        LocalDateTime datetime = LocalDateTime.ofInstant(entity.getCreatedAt(), ZoneOffset.UTC);
        dto.setCreatedAt(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(datetime));
    }
}