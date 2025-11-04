package com.final_project.descubri_cba.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

public class EnumMapper {

    private static final Map<String, String> friendlyNames = new HashMap<>();

    private static final Map<String, String> fromFriendlyNames = new HashMap<>();

    static {
        // Payment Methods
        friendlyNames.put("EFECTIVO", "Efectivo");
        friendlyNames.put("TARJETA_DE_CREDITO", "Tarjeta de Crédito");
        friendlyNames.put("TARJETA_DE_DEBITO", "Tarjeta de Débito");
        friendlyNames.put("TRANSFERENCIA_BANCARIA", "Transferencia Bancaria");

        // Cuisine Types
        friendlyNames.put("COMIDA_RAPIDA", "Comida Rápida");
        friendlyNames.put("PARRILLA", "Parrilla");
        friendlyNames.put("PASTAS", "Pastas");
        friendlyNames.put("PESCADOS_Y_MARISCOS", "Pescados y Mariscos");
        friendlyNames.put("PIZZA", "Pizza");
        friendlyNames.put("SUSHI", "Sushi");
        friendlyNames.put("VEGANA", "Vegana");
        friendlyNames.put("VEGETARIANA", "Vegetariana");
        friendlyNames.put("MINUTAS", "Minutas");
        friendlyNames.put("GOURMET", "Gourmet");
        friendlyNames.put("TRADICIONAL", "Tradicional");
        friendlyNames.put("INTERNACIONAL", "Internacional");

        for (Map.Entry<String, String> entry : friendlyNames.entrySet()) {
            fromFriendlyNames.put(entry.getValue(), entry.getKey());
        }
    }

    public static String toFriendlyName(String enumString) {
        if (enumString == null) {
            return null;
        }
        return friendlyNames.getOrDefault(enumString, enumString);
    }

    public static List<String> toFriendlyNameList(List<String> enumStrings) {
        if (enumStrings == null) {
            return null;
        }
        return enumStrings.stream()
                .map(EnumMapper::toFriendlyName)
                .collect(Collectors.toList());
    }

    public static String fromFriendlyName(String friendlyName) {
        if (friendlyName == null) {
            return null;
        }
        return fromFriendlyNames.getOrDefault(friendlyName, friendlyName);
    }

    public static List<String> fromFriendlyNameList(List<String> friendlyNames) {
        if (friendlyNames == null) {
            return null;
        }
        return friendlyNames.stream()
                .map(EnumMapper::fromFriendlyName)
                .collect(Collectors.toList());
    }
}
