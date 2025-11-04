package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.*;
import com.final_project.descubri_cba.enums.Concurrence;
import com.final_project.descubri_cba.exception.CustomException;
import com.final_project.descubri_cba.model.*;
import com.final_project.descubri_cba.repository.*;
import com.final_project.descubri_cba.utils.DestinationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para la gestión de destinos turísticos
 * Maneja la lógica de negocio para obtener información de destinos de Córdoba
 */
@Service
public class DestinationService implements IDestinationService {

    @Autowired
    private IDestinationRepository destinationRepository;

    /*
     * Obtiene cards simplificadas de todos los destinos para mostrar en el frontend
     */
    @Override
    public List<DestinationCardDTO> getAllDestinationCards() {
        return destinationRepository.findDistinctLocalities();
    }

    @Override
    public List<String> getConcurrenceLevels() {
        List<String> concurrenceLevels = Arrays.stream(Concurrence.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        return concurrenceLevels;
    }

    /* Obtiene un destino específico por su ID */
    @Override
    public DestinationDTO getDestinationById(Long id) {
        Optional<Destination> destination = destinationRepository.findById(id);

        if (destination.isEmpty()) {
            throw new CustomException("Destino no encontrado con ID: " + id, HttpStatus.NOT_FOUND);
        }

        return DestinationMapper.mapToDestinationDTO(destination.get());
    }

    @Override
    public List<DestinationCardDTO> findDestinationsByLocality(String searchTerm) {
        return destinationRepository.findDestinationsByLocalityLike(searchTerm);
    }

}