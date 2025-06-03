package com.final_project.descubri_cba.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum  TypeOfEmergency {
    SALUD,
    POLICIA,
    BOMBEROS,
    AUXILIO_MECANICO;

    @JsonCreator
    public static TypeOfEmergency fromString(String value) {
        return value == null ? null : TypeOfEmergency.valueOf(value.toUpperCase());
    }
}
