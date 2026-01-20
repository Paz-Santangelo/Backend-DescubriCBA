package com.final_project.descubri_cba.dto;

/**
 * Proyección de interfaz para mapear resultados de consultas nativas a DestinationCardDTO
 * Spring Data JPA usará esta interfaz para mapear automáticamente las columnas
 */
public interface DestinationCardProjection {
    Long getId();
    String getLocality();
    String getImageUrl();
}
