package com.memory.beautifulbride.entitys.dress.definition;

import com.memory.beautifulbride.config.custom.AbstractEnumToString;

public enum DressNeckLineEnum {
    ROUND, VLINEM, TOP, HALTERNECK, OFFSHOULDER;
    
    public static class Convert extends AbstractEnumToString<DressNeckLineEnum> {
        protected Convert(Class<DressNeckLineEnum> clazz) {
            super(clazz);
        }
    }
}
