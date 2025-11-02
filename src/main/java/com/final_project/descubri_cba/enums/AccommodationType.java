package com.final_project.descubri_cba.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AccommodationType {
    HOTEL("HOTEL"),
    HOSTEL("HOSTEL"),
    CAMPING("CAMPING"),
    CABANIA("CABAÑA");

    private final String displayName;

    AccommodationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static AccommodationType fromString(String value) {
        if (value == null) {
            return null;
        }
        if ("CABAÑA".equalsIgnoreCase(value)) {
            return CABANIA;
        }
        return AccommodationType.valueOf(value.toUpperCase());
    }
}
