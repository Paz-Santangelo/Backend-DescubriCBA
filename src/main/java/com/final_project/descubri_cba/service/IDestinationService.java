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
     * Obtiene todos los destinos turísticos disponibles
     * @return Lista de todos los destinos
     */
    List<DestinationDTO> getAllDestinations();

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
     * Obtiene destinos filtrados por departamento
     * @param department Departamento de Córdoba
     * @return Lista de destinos en el departamento
     */
    List<DestinationDTO> getDestinationsByDepartment(String department);
}