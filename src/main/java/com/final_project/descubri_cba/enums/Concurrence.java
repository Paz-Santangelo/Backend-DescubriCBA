package com.final_project.descubri_cba.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Concurrence {
    BAJA,
    MEDIA,
    ALTA;

    @JsonCreator
    public static Concurrence fromString(String value) {
        return value == null ? null : Concurrence.valueOf(value.toUpperCase());
    }
}
