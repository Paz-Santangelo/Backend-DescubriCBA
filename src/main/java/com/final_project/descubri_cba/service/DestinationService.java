package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.*;
import com.final_project.descubri_cba.exception.CustomException;
import com.final_project.descubri_cba.model.*;
import com.final_project.descubri_cba.repository.*;
import com.final_project.descubri_cba.utils.DestinationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de destinos turísticos
 * Maneja la lógica de negocio para obtener información de destinos de Córdoba
 */
@Service
public class DestinationService implements IDestinationService {

    @Autowired
    private IDestinationRepository destinationRepository;

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Autowired
    private IAccommodationRepository accommodationRepository;

    @Autowired
    private IBodyOfWaterRepository bodyOfWaterRepository;

    @Autowired
    private IEmergencyServicesRepository emergencyServicesRepository;


    /**
     * Obtiene destinos filtrados por tipo
     * @param type Tipo de destino (restaurant, accommodation, bodyofwater, emergencyservices)
     * @return Lista de destinos del tipo especificado
     */
    @Override
    public List<DestinationDTO> getDestinationsByType(String type) {
        List<DestinationDTO> destinations = new ArrayList<>();

        switch (type.toLowerCase()) {
            case "restaurant":
            case "restaurantes":
                List<Restaurant> restaurants = restaurantRepository.findAll();
                for (Restaurant restaurant : restaurants) {
                    destinations.add(DestinationMapper.mapToDestinationDTO(restaurant));
                }
                break;

            case "accommodation":
            case "alojamientos":
                List<Accommodation> accommodations = accommodationRepository.findAll();
                for (Accommodation accommodation : accommodations) {
                    destinations.add(DestinationMapper.mapToDestinationDTO(accommodation));
                }
                break;

            case "bodyofwater":
            case "cuerpos-de-agua":
                List<BodyOfWater> bodyOfWaters = bodyOfWaterRepository.findAll();
                for (BodyOfWater bodyOfWater : bodyOfWaters) {
                    destinations.add(DestinationMapper.mapToDestinationDTO(bodyOfWater));
                }
                break;

            case "emergencyservices":
            case "servicios-emergencia":
                List<EmergencyServices> emergencyServices = emergencyServicesRepository.findAll();
                for (EmergencyServices service : emergencyServices) {
                    destinations.add(DestinationMapper.mapToDestinationDTO(service));
                }
                break;

            default:
                throw new CustomException("Tipo de destino no válido: " + type, HttpStatus.BAD_REQUEST);
        }

        return destinations;
    }

    /**
     * Obtiene un destino específico por su ID
     * @param id ID del destino
     * @return Información del destino
     */
    @Override
    public DestinationDTO getDestinationById(Long id) {
        Optional<Destination> destination = destinationRepository.findById(id);
        
        if (destination.isEmpty()) {
            throw new CustomException("Destino no encontrado con ID: " + id, HttpStatus.NOT_FOUND);
        }

        return DestinationMapper.mapToDestinationDTO(destination.get());
    }

    /**
     * Obtiene cards simplificadas de todos los destinos para mostrar en el frontend
     * @return Lista de cards con información básica de destinos
     */
    @Override
     public List<DestinationCardDTO> getAllDestinationCards() {
         // Llama directamente a la consulta optimizada en el repositorio.
         // Esto es mucho más eficiente que traer todos los datos a la memoria.
         return destinationRepository.findDistinctLocalities();
     }

    /**
     * Busca destinos por nombre utilizando el método del repositorio.
     * @param name Término de búsqueda.
     * @return Lista de DTOs de destinos coincidentes.
     */
    @Override
    public List<DestinationDTO> searchDestinationsByName(String name) {
        List<Destination> destinations = destinationRepository.findByNameContainingIgnoreCase(name);
        return DestinationMapper.genericMapListToTypedDTO(destinations, DestinationDTO.class);
    }

    @Override
    public List<DestinationDTO> getDestinationsByLocality(String locality) {
        List<Destination> destinations = destinationRepository.findByLocalityIgnoreCase(locality);
        return DestinationMapper.genericMapListToTypedDTO(destinations, DestinationDTO.class);
    }
}