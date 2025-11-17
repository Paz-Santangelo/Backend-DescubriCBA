package com.final_project.descubri_cba.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO simplificado para mostrar cards de destinos en el frontend
 * Contiene solo la información esencial para mostrar en tarjetas
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DestinationCardDTO {
    private Long id;
    private String locality;
    private String imageUrl;
}