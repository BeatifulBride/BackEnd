package com.memory.beautifulbride.entitys.dress.definition;

import com.memory.beautifulbride.config.custom.AbstractEnumToString;

public enum DressSleeveEnum {
    LONG, HALF, NONE, OFF, TOP;
    public static class Convert extends AbstractEnumToString<DressSleeveEnum> {
        protected Convert(Class<DressSleeveEnum> clazz) {
            super(clazz);
        }
    }
}
