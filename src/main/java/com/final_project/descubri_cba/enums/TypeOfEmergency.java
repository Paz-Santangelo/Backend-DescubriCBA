package com.final_project.descubri_cba.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum TypeOfEmergency {
    SALUD("SALUD"),
    POLICIA("POLICÍA"),
    BOMBEROS("BOMBEROS"),
    AUXILIO_MECANICO("AUXILIO MECÁNICO");

    private final String displayName;

    TypeOfEmergency(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static TypeOfEmergency fromString(String value) {
        if (value == null) return null;
        for (TypeOfEmergency type : TypeOfEmergency.values()) {
            if (type.name().equalsIgnoreCase(value.replace(" ", "_")) || type.getDisplayName().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }
}
