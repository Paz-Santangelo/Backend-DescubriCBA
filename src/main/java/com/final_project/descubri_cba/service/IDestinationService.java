package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.DestinationDTO;
import com.final_project.descubri_cba.dto.DestinationCardDTO;
import java.util.List;

/**
 * Interfaz del servicio para la gestión de destinos turísticos
 * Define los métodos para obtener información de destinos de Córdoba
 */
public interface IDestinationService {

    /*
     * Obtiene cards simplificadas de todos los destinos para mostrar en el frontend
     */
    List<DestinationCardDTO> getAllDestinationCards();

    public List<String> getConcurrenceLevels();

    /* Obtiene un destino específico por su ID */
    DestinationDTO getDestinationById(Long id);

    List<DestinationCardDTO> findDestinationsByLocality(String searchTerm);

}