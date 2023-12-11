package com.memory.beautifulbride.entitys.dress.definition;

import com.memory.beautifulbride.config.custom.AbstractEnumToString;

public enum DressLineEnum {
    ALINE, SHEATH, BALLGOWN, TRUMPET, MERMAID, TEALENGTH;
    public static class Convert extends AbstractEnumToString<DressLineEnum> {
        protected Convert(Class<DressLineEnum> clazz) {
            super(clazz);
        }
    }
}
