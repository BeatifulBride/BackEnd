package com.memory.beautifulbride.entitys.dress.definition;

import com.memory.beautifulbride.config.custom.AbstractEnumToString;

public enum DressMaterialEnum {
    CRYSTAL, PEARL, FLORAL, RIBBON, EMB;
    
    public static class Convert extends AbstractEnumToString<DressMaterialEnum> {
        protected Convert(Class<DressMaterialEnum> clazz) {
            super(clazz);
        }
    }
}
