package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.DestinationDTO;
import com.final_project.descubri_cba.dto.DestinationCardDTO;
import java.util.List;

/**
 * Interfaz del servicio para la gestión de destinos turísticos
 * Define los métodos para obtener información de destinos de Córdoba
 */
public interface IDestinationService {

    /**
     * Obtiene cards simplificadas de todos los destinos para mostrar en el frontend
     * @return Lista de cards con información básica de destinos
     */
    List<DestinationCardDTO> getAllDestinationCards();

    /**
     * Obtiene destinos filtrados por tipo
     * @param type Tipo de destino (restaurant, accommodation, bodyofwater, emergencyservices)
     * @return Lista de destinos del tipo especificado
     */
    List<DestinationDTO> getDestinationsByType(String type);

    /**
     * Obtiene un destino específico por su ID
     * @param id ID del destino
     * @return Información del destino
     */
    DestinationDTO getDestinationById(Long id);

    /**
     * Busca destinos cuyo nombre contenga el término de búsqueda.
     * @param name Término de búsqueda para el nombre del destino.
     * @return Lista de destinos que coinciden con la búsqueda.
     */
    List<DestinationDTO> searchDestinationsByName(String name);

    /**
     * Obtiene destinos filtrados por localidad, ignorando mayúsculas y minúsculas.
     * @param locality Localidad de Córdoba
     * @return Lista de destinos en la localidad
     */
    List<DestinationDTO> getDestinationsByLocality(String locality);
}