package com.final_project.descubri_cba.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TypeBodyOfWater {
    RIO("RÍO"),
    LAGUNA("LAGUNA"),
    ARROYO("ARROYO"),
    CASCADA("CASCADA"),
    BALNEARIO("BALNEARIO"),
    LAGO("LAGO"),
    EMBALSE("EMBALSE"),
    DIQUE("DIQUE");

    private final String displayName;

    TypeBodyOfWater(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static TypeBodyOfWater fromString(String value) {
        if (value == null)
            return null;
        for (TypeBodyOfWater type : TypeBodyOfWater.values()) {
            if (type.name().equalsIgnoreCase(value) || type.getDisplayName().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(
                "Unknown enum type " + value + ", Allowed values are " + TypeBodyOfWater.values());
    }
}
