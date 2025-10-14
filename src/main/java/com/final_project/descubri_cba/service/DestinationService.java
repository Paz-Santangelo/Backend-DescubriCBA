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
     * Obtiene todos los destinos turísticos disponibles
     * Combina todos los tipos de destinos en una sola lista
     * @return Lista de todos los destinos
     */
    @Override
    public List<DestinationDTO> getAllDestinations() {
        List<DestinationDTO> allDestinations = new ArrayList<>();

        // Obtener restaurantes
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant restaurant : restaurants) {
            allDestinations.add(DestinationMapper.mapToDestinationDTO(restaurant));
        }

        // Obtener alojamientos
        List<Accommodation> accommodations = accommodationRepository.findAll();
        for (Accommodation accommodation : accommodations) {
            allDestinations.add(DestinationMapper.mapToDestinationDTO(accommodation));
        }

        // Obtener cuerpos de agua
        List<BodyOfWater> bodyOfWaters = bodyOfWaterRepository.findAll();
        for (BodyOfWater bodyOfWater : bodyOfWaters) {
            allDestinations.add(DestinationMapper.mapToDestinationDTO(bodyOfWater));
        }

        // Obtener servicios de emergencia
        List<EmergencyServices> emergencyServices = emergencyServicesRepository.findAll();
        for (EmergencyServices service : emergencyServices) {
            allDestinations.add(DestinationMapper.mapToDestinationDTO(service));
        }

        return allDestinations;
    }

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
        List<DestinationCardDTO> cards = new ArrayList<>();
        
        // Obtener cards de restaurantes
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant restaurant : restaurants) {
            cards.add(mapToDestinationCardDTO(restaurant));
        }
        
        // Obtener cards de alojamientos
        List<Accommodation> accommodations = accommodationRepository.findAll();
        for (Accommodation accommodation : accommodations) {
            cards.add(mapToDestinationCardDTO(accommodation));
        }
        
        // Obtener cards de cuerpos de agua
        List<BodyOfWater> bodiesOfWater = bodyOfWaterRepository.findAll();
        for (BodyOfWater bodyOfWater : bodiesOfWater) {
            cards.add(mapToDestinationCardDTO(bodyOfWater));
        }
        
        // Obtener cards de servicios de emergencia
        List<EmergencyServices> emergencyServices = emergencyServicesRepository.findAll();
        for (EmergencyServices service : emergencyServices) {
            cards.add(mapToDestinationCardDTO(service));
        }
        
        return cards;
    }

    /**
     * Mapea cualquier destino a DestinationCardDTO para las cards del frontend
     */
    private DestinationCardDTO mapToDestinationCardDTO(Destination destination) {
        DestinationCardDTO card = new DestinationCardDTO();
        card.setId(destination.getId());
        card.setName(destination.getName());
        card.setDepartment(destination.getDepartment());
        card.setLocality(destination.getLocality());
        card.setAddress(destination.getAddress());
        card.setAverageScore(destination.getAverageScore());
        card.setOpeningTime(destination.getOpeningTime());
        card.setClosingTime(destination.getClosingTime());
        
        // Determinar el tipo y descripción basado en la clase
        if (destination instanceof Restaurant) {
            card.setType("restaurant");
            card.setDescription("Restaurante");
        } else if (destination instanceof Accommodation) {
            card.setType("accommodation");
            card.setDescription("Alojamiento");
        } else if (destination instanceof BodyOfWater) {
            card.setType("bodyofwater");
            card.setDescription("Cuerpo de agua");
        } else if (destination instanceof EmergencyServices) {
            card.setType("emergencyservices");
            card.setDescription("Servicio de emergencia");
        }
        
        // Obtener primera imagen si existe
        if (destination.getImagesDestinations() != null && !destination.getImagesDestinations().isEmpty()) {
            ImageDestination firstImage = destination.getImagesDestinations().get(0);
            if (firstImage != null && firstImage.getImageUrl() != null) {
                card.setImageUrl(firstImage.getImageUrl());
            }
        }
        
        return card;
    }

    /**
     * Obtiene destinos filtrados por departamento
     * @param department Departamento de Córdoba
     * @return Lista de destinos en el departamento
     */
    @Override
    public List<DestinationDTO> getDestinationsByDepartment(String department) {
        List<DestinationDTO> allDestinations = getAllDestinations();
        List<DestinationDTO> filteredDestinations = new ArrayList<>();

        for (DestinationDTO destination : allDestinations) {
            if (destination.getDepartment() != null && 
                destination.getDepartment().toLowerCase().contains(department.toLowerCase())) {
                filteredDestinations.add(destination);
            }
        }

        return filteredDestinations;
    }
}