package com.memory.beautifulbride.entitys.dress.definition;

import com.memory.beautifulbride.config.custom.AbstractEnumToString;

public enum DressSeasonEnum {
    SPRING, SUMMER, FALL, WINTER;


    static class Converter extends AbstractEnumToString<DressNeckLineEnum> {
        protected Converter(Class<DressNeckLineEnum> clazz) {
            super(clazz);
        }
    }
}
