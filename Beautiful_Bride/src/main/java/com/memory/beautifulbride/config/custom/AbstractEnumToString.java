package com.memory.beautifulbride.config.custom;

import jakarta.persistence.AttributeConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractEnumToString<T extends Enum<T>> implements AttributeConverter<T, String> {
    private final Class<T> clazz;

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return capitalizeFirst(attribute);
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        return Enum.valueOf(clazz, dbData);
    }

    private String capitalizeFirst(Enum<T> e) {
        String x = e.name();
        return x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase();
    }
}
