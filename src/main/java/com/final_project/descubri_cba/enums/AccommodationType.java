package com.final_project.descubri_cba.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccommodationType {
    HOTEL("HOTEL"),
    HOSTEL("HOSTEL"),
    CAMPING("CAMPING"),
    CABANIA("CABAÑA");

    private final String displayName;

    AccommodationType(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static AccommodationType fromString(String value) {
        if (value == null) {
            return null;
        }
        for (AccommodationType type : AccommodationType.values()) {
            if (type.displayName.equalsIgnoreCase(value) || type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown accommodation type: " + value);
    }
}
