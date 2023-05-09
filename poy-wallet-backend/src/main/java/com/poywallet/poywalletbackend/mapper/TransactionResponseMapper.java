package com.poywallet.poywalletbackend.mapper;

import com.poywallet.poywalletbackend.web.api.transaction.ResponseTransaction;
import com.poywallet.poywalletbackend.model.Transaction;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static com.poywallet.poywalletbackend.common.Constants.DATE_TIME_FORMAT;

/**
 * Mapper used for mapping TransactionResponse fields
 */
@Mapper(componentModel = "spring")
public interface TransactionResponseMapper {
    Transaction toEntity(ResponseTransaction dto);

    @Mapping(target = "createdAt", ignore = true)
    ResponseTransaction toDto(Transaction entity);

    @AfterMapping
    default void formatCreatedAt(@MappingTarget ResponseTransaction dto, Transaction entity) {
        LocalDateTime datetime = LocalDateTime.ofInstant(entity.getCreatedAt(), ZoneOffset.UTC);
        dto.setCreatedAt(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(datetime));
    }
}
