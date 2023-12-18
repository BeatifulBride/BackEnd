package com.memory.beautifulbride.entitys.dress.definition;

import com.memory.beautifulbride.config.custom.AbstractEnumToString;

public enum DressNeckLineEnum {
    ROUND, V, TOP, HALTER, OFF;
    
    public static class Convert extends AbstractEnumToString<DressNeckLineEnum> {
        protected Convert(Class<DressNeckLineEnum> clazz) {
            super(clazz);
        }
    }
}
