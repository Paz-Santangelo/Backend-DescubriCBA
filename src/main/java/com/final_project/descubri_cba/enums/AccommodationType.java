package com.final_project.descubri_cba.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AccommodationType {
    HOTEL,
    HOSTEL,
    CAMPING,
    CABANIA;

    @JsonCreator
    public static AccommodationType fromString(String value) {
        return value == null ? null : AccommodationType.valueOf(value.toUpperCase());
    }
}
