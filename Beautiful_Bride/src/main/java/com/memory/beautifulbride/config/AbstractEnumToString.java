package com.memory.beautifulbride.config;

import jakarta.persistence.AttributeConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractEnumToString<T extends Enum<T>> implements AttributeConverter<T, String> {
    private final Class<T> clazz;

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return attribute.toString();
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        return Enum.valueOf(clazz, dbData);
    }
}
