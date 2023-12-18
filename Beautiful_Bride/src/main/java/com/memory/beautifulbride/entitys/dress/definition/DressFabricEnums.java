package com.memory.beautifulbride.entitys.dress.definition;

import com.memory.beautifulbride.config.custom.AbstractEnumToString;

public enum DressFabricEnums {
    SILK, LACE, TULLE, ORGANZA, TAFFETA, SHIFFON;

    public static class Convert extends AbstractEnumToString<DressFabricEnums> {
        protected Convert(Class<DressFabricEnums> clazz) {
            super(clazz);
        }
    }
}
