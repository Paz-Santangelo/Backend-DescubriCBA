package com.final_project.descubri_cba.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnumMapper {

    private static final Map<String, String> friendlyNames = new HashMap<>();

    private static final Map<String, String> fromFriendlyNames = new HashMap<>();

    private static final List<String> paymentMethods = new ArrayList<>();

    private static final List<String> cuisineTypes = new ArrayList<>();

    private static final List<String> cleaningLevels = new ArrayList<>();


    static {
        // Payment Methods
        friendlyNames.put("EFECTIVO", "Efectivo");
        friendlyNames.put("TARJETA_DE_CREDITO", "Tarjeta de Crédito");
        friendlyNames.put("TARJETA_DE_DEBITO", "Tarjeta de Débito");
        friendlyNames.put("TRANSFERENCIA_BANCARIA", "Transferencia Bancaria");
        friendlyNames.put("MERCADO_PAGO", "Mercado Pago");
        friendlyNames.put("MODO", "Modo");

        paymentMethods.addAll(List.of("Efectivo", "Tarjeta de Crédito", "Tarjeta de Débito", "Transferencia Bancaria", "Mercado Pago", "Modo"));

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
        friendlyNames.put("ASIATICA", "Asiática");

        cuisineTypes.addAll(List.of("Comida Rápida", "Parrilla", "Pastas", "Pescados y Mariscos", "Pizza", "Sushi", "Vegana", "Vegetariana", "Minutas", "Gourmet", "Tradicional", "Internacional", "Asiática"));

        // Cleaning Levels
        cleaningLevels.addAll(List.of("EXCELENTE", "BUENO", "REGULAR", "MALO"));

        for (Map.Entry<String, String> entry : friendlyNames.entrySet()) {
            fromFriendlyNames.put(entry.getValue(), entry.getKey());
        }
    }

    public static List<String> getPaymentMethods() {
        return paymentMethods;
    }

    public static List<String> getCuisineTypes() {
        return cuisineTypes;
    }

    public static List<String> getCleaningLevels() {
        return cleaningLevels;
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
