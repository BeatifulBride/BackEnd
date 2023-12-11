package com.memory.beautifulbride.entitys.dress.definition;

import com.memory.beautifulbride.config.custom.AbstractEnumToString;

public enum DressLengthEnums {
    FULL, MEDIUM, SHORT;
    public static class Convert extends AbstractEnumToString<DressLengthEnums> {
        protected Convert(Class<DressLengthEnums> clazz) {
            super(clazz);
        }
    }
}