package com.memory.beautifulbride.entitys.dress.definition;

import com.memory.beautifulbride.config.custom.AbstractEnumToString;

public enum DressLineEnum {
    A, B, E, H;
    public static class Convert extends AbstractEnumToString<DressLineEnum> {
        protected Convert(Class<DressLineEnum> clazz) {
            super(clazz);
        }
    }
}
