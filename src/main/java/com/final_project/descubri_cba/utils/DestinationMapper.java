package com.final_project.descubri_cba.utils;

import com.final_project.descubri_cba.dto.*;
import com.final_project.descubri_cba.model.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.stream.Collectors;

public class DestinationMapper {
    public static DestinationDTO mapToDestinationDTO(Destination destination) {
        if (destination == null) return null;

        // Desproxificar si es necesario
        destination = (Destination) Hibernate.unproxy(destination);

        List<RatingDTO> ratingsDto = RatingMapper.convertRatingEntityListToRatingDTOList(destination.getRatings());
        List<CommentDTO> commentsDto = CommentMapper.convertCommentEntityListToCommentDTOList(destination.getComments());

        DestinationDTO destinationDto;

        if (destination instanceof Restaurant restaurant) {
            RestaurantDTO dto = new RestaurantDTO();
            dto.setCuisineType(restaurant.getCuisineType());
            dto.setDelivery(restaurant.isDelivery());
            dto.setReservations(restaurant.isReservations());
            destinationDto = dto;
        } else if (destination instanceof Accommodation accommodation) {
            AccommodationDTO dto = new AccommodationDTO();
            dto.setType(accommodation.getType());
            destinationDto = dto;
        } else if (destination instanceof BodyOfWater body) {
            BodyOfWaterDTO dto = new BodyOfWaterDTO();
            dto.setTypeBodyOfWater(body.getTypeBodyOfWater());
            dto.setEntrancePrice(body.getEntrancePrice());
            dto.setFreeAdmission(body.isFreeAdmission());
            dto.setCleaningLevel(body.getCleaningLevel());
            destinationDto = dto;
        } else if (destination instanceof EmergencyServices emergency) {
            EmergencyServicesDTO dto = new EmergencyServicesDTO();
            dto.setTypeOfEmergency(emergency.getTypeOfEmergency());
            destinationDto = dto;
        } else {
            throw new IllegalArgumentException("Tipo de destino no soportado: " + destination.getClass().getSimpleName());
        }

        // Mapear los campos comunes
        destinationDto.setId(destination.getId());
        destinationDto.setName(destination.getName());
        destinationDto.setDepartment(destination.getDepartment());
        destinationDto.setLocality(destination.getLocality());
        destinationDto.setAddress(destination.getAddress());
        destinationDto.setUrlGoogleMaps(destination.getUrlGoogleMaps());
        destinationDto.setOpeningTime(destination.getOpeningTime());
        destinationDto.setClosingTime(destination.getClosingTime());
        destinationDto.setLevelConcurrence(destination.getLevelConcurrence());
        destinationDto.setDisabledAccessibility(destination.isDisabledAccessibility());
        destinationDto.setNumberPhone(destination.getNumberPhone());
        destinationDto.setCellPhone(destination.getCellPhone());
        destinationDto.setWebsite(destination.getWebsite());
        destinationDto.setPaymentMethods(destination.getPaymentMethods());
        destinationDto.setImagesDestinations(
                ImageMapper.convertEntityImageListToImageDTOList(destination.getImagesDestinations())
        );
        destinationDto.setAverageScore(destination.getAverageScore().intValue()); // Conversión Double a int para DTO
        destinationDto.setComments(commentsDto);
        destinationDto.setRatings(ratingsDto);
        destinationDto.setOwnerId(destination.getUser() != null ? destination.getUser().getId() : null);

        return destinationDto;
    }

    // Retorna un DTO generico
    @SuppressWarnings("unchecked")
    public static <D extends DestinationDTO> D genericMapToTypedDTO(Destination destination, Class<D> dtoClass) {
        DestinationDTO baseDto = mapToDestinationDTO(destination);
        return (D) baseDto;
    }

    // Retorna una lista de DTOs de cualquier tipo de destino, ya sea Restaurant, Accommodation, etc.
    public static <T extends Destination, D extends DestinationDTO> List<D> genericMapListToTypedDTO(List<T> entities, Class<D> dtoClass) {
        return entities.stream()
                .map(e -> DestinationMapper.genericMapToTypedDTO(e, dtoClass))
                .collect(Collectors.toList());
    }

    // Mapea un DTO generico a una entidad generica (Puede ser restaurant, accommodation, etc.)
    @SuppressWarnings("unchecked")
    public static <E extends Destination> E mapDtoToEntityForSave(DestinationDTO dto, User owner, Class<E> entityClass) {
        if (dto == null) return null;

        E destination;
        try {
            destination = entityClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (dto instanceof RestaurantDTO restaurantDTO && entityClass == Restaurant.class) {
            Restaurant restaurant = new Restaurant();
            restaurant.setCuisineType(restaurantDTO.getCuisineType());
            restaurant.setDelivery(restaurantDTO.isDelivery());
            restaurant.setReservations(restaurantDTO.isReservations());
            destination = (E) restaurant;
        } else if (dto instanceof AccommodationDTO accommodationDTO && entityClass == Accommodation.class) {
            Accommodation accommodation = new Accommodation();
            accommodation.setType(accommodationDTO.getType());
            destination = (E) accommodation;
        } else if (dto instanceof BodyOfWaterDTO bodyDTO && entityClass == BodyOfWater.class) {
            BodyOfWater body = new BodyOfWater();
            body.setTypeBodyOfWater(bodyDTO.getTypeBodyOfWater());
            body.setEntrancePrice(bodyDTO.getEntrancePrice());
            body.setFreeAdmission(bodyDTO.isFreeAdmission());
            body.setCleaningLevel(bodyDTO.getCleaningLevel());
            destination = (E) body;
        } else if (dto instanceof EmergencyServicesDTO emergencyDTO && entityClass == EmergencyServices.class) {
            EmergencyServices emergency = new EmergencyServices();
            emergency.setTypeOfEmergency(emergencyDTO.getTypeOfEmergency());
            destination = (E) emergency;
        } else {
            throw new IllegalArgumentException("Tipo de DTO no soportado: " + dto.getClass().getSimpleName());
        }

        // Mapear campos comunes
        destination.setName(dto.getName());
        destination.setDepartment(dto.getDepartment());
        destination.setLocality(dto.getLocality());
        destination.setAddress(dto.getAddress());
        destination.setUrlGoogleMaps(dto.getUrlGoogleMaps());
        destination.setOpeningTime(dto.getOpeningTime());
        destination.setClosingTime(dto.getClosingTime());
        destination.setLevelConcurrence(dto.getLevelConcurrence());
        destination.setDisabledAccessibility(dto.isDisabledAccessibility());
        destination.setNumberPhone(dto.getNumberPhone());
        destination.setCellPhone(dto.getCellPhone());
        destination.setWebsite(dto.getWebsite());
        destination.setPaymentMethods(dto.getPaymentMethods());
        destination.setAverageScore(0.0); // Double en entrada
        destination.setUser(owner);

        return destination;
    }

    // Mapea un DTO a una entidad para modificar un determinado destino, porque no se instancia un nuevo objeto de tipo destino como ocurre con el otro metodo mapDtoToEntityForSave
    public static void mapDtoToEntityForUpdate(DestinationDTO dto, Destination destination, User owner) {
        destination.setName(dto.getName());
        destination.setDepartment(dto.getDepartment());
        destination.setLocality(dto.getLocality());
        destination.setAddress(dto.getAddress());
        destination.setUrlGoogleMaps(dto.getUrlGoogleMaps());
        destination.setOpeningTime(dto.getOpeningTime());
        destination.setClosingTime(dto.getClosingTime());
        destination.setLevelConcurrence(dto.getLevelConcurrence());
        destination.setDisabledAccessibility(dto.isDisabledAccessibility());
        destination.setNumberPhone(dto.getNumberPhone());
        destination.setCellPhone(dto.getCellPhone());
        destination.setWebsite(dto.getWebsite());
        destination.setPaymentMethods(dto.getPaymentMethods());
        destination.setUser(owner);
        destination.setAverageScore(0.0); // Double en entrada

        // Específicos por subclase
        if (dto instanceof RestaurantDTO restaurantDTO && destination instanceof Restaurant restaurant) {
            restaurant.setCuisineType(restaurantDTO.getCuisineType());
            restaurant.setDelivery(restaurantDTO.isDelivery());
            restaurant.setReservations(restaurantDTO.isReservations());
        } else if (dto instanceof AccommodationDTO accommodationDTO && destination instanceof Accommodation accommodation) {
            accommodation.setType(accommodationDTO.getType());
        } else if (dto instanceof BodyOfWaterDTO bodyDTO && destination instanceof BodyOfWater body) {
            body.setTypeBodyOfWater(bodyDTO.getTypeBodyOfWater());
            body.setEntrancePrice(bodyDTO.getEntrancePrice());
            body.setFreeAdmission(bodyDTO.isFreeAdmission());
            body.setCleaningLevel(bodyDTO.getCleaningLevel());
        } else if (dto instanceof EmergencyServicesDTO emergencyDTO && destination instanceof EmergencyServices emergency) {
            emergency.setTypeOfEmergency(emergencyDTO.getTypeOfEmergency());
        } else {
            throw new IllegalArgumentException("Tipo no soportado en actualización: " + dto.getClass().getSimpleName());
        }
    }
}
