package com.final_project.descubri_cba.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TypeBodyOfWater {
    RIO,
    LAGUNA,
    ARROYO,
    CASCADA,
    BALNEARIO,
    LAGO,
    EMBALSE,
    DIQUE;

    @JsonCreator
    public static TypeBodyOfWater fromString(String value) {
        return value == null ? null : TypeBodyOfWater.valueOf(value.toUpperCase());
    }
}
