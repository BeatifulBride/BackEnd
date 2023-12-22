package com.memory.beautifulbride.config.custom;

import jakarta.persistence.AttributeConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractEnumToString<T extends Enum<T>> implements AttributeConverter<T, String> {
    private final Class<T> clazz;

    @Override
    public String convertToDatabaseColumn(T attribute) {
        System.out.println("이넘 가상에서 넘어옴 컨버트 데이터 베이스 컬럼 :: " + capitalizeFirst(attribute));
        return capitalizeFirst(attribute);
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        System.out.println("이넘 가상에서 넘어옴 컨버트 엔티티 설정 :: " + Enum.valueOf(clazz, dbData));
        return Enum.valueOf(clazz, dbData);
    }

    private String capitalizeFirst(Enum<T> e) {
        String x = e.name();
        return x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase();
    }
}
