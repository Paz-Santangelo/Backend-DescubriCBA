package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.RestaurantDTO;
import com.final_project.descubri_cba.model.ImageDestination;
import com.final_project.descubri_cba.model.Restaurant;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.repository.IRestaurantRepository;
import com.final_project.descubri_cba.repository.IUserRepository;
import com.final_project.descubri_cba.repository.IImageDestinationRepository;
import com.final_project.descubri_cba.utils.DestinationMapper;
import com.final_project.descubri_cba.specification.RestaurantSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

@Service
public class RestaurantService implements IRestaurantService {

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private IImageDestinationRepository imageDestinationRepository;

    @Override
    public List<RestaurantDTO> findAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return DestinationMapper.genericMapListToTypedDTO(restaurants, RestaurantDTO.class);
    }

    @Override
    public RestaurantDTO findRestaurantById(Long idRestaurant) {
        Restaurant restaurantFound = restaurantRepository.findById(idRestaurant).orElseThrow(() -> new RuntimeException("No se encontró el restaurante."));
        return (RestaurantDTO) DestinationMapper.mapToDestinationDTO(restaurantFound);
    }

    @Override
    @Transactional
    public RestaurantDTO saveRestaurant(RestaurantDTO restaurantDTO, List<MultipartFile> files) {
        try {
            User ownerFound = userRepository.findById(restaurantDTO.getOwnerId()).orElseThrow(() -> new RuntimeException("Propietario no encontrado."));
            Restaurant restaurant = DestinationMapper.mapDtoToEntityForSave(restaurantDTO, Restaurant.class, ownerFound);
            Restaurant restaurantSaved = restaurantRepository.save(restaurant);

            if (files != null && !files.isEmpty()) {
                List<ImageDestination> images = imageService.uploadImagesDestinations(files, restaurantSaved);
                restaurantSaved.setImagesDestinations(images);
                restaurantSaved = restaurantRepository.save(restaurantSaved);
            }
            return (RestaurantDTO) DestinationMapper.mapToDestinationDTO(restaurantSaved);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el restaurante: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public RestaurantDTO updateRestaurant(Long idRestaurant, RestaurantDTO restaurantDTO, List<MultipartFile> files) throws IOException {
        Restaurant restaurantFound = restaurantRepository.findById(idRestaurant)
                .orElseThrow(() -> new RuntimeException("No se encontró el restaurante"));

        User ownerFound = userRepository.findById(restaurantDTO.getOwnerId())
                .orElseThrow(() -> new RuntimeException("No se encontró el propietario."));

        if (files != null && !files.isEmpty()) {
            List<ImageDestination> existingImages = new ArrayList<>(restaurantFound.getImagesDestinations());
            for (ImageDestination image : existingImages) {
                restaurantFound.removeImageDestination(image);
                imageService.deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
            }

            List<ImageDestination> newImages = imageService.uploadImagesDestinations(files, restaurantFound);
            for (ImageDestination image : newImages) {
                restaurantFound.addImageDestination(image);
            }
        }

        DestinationMapper.mapDtoToEntityForUpdate(restaurantDTO, restaurantFound, ownerFound);
        Restaurant updated = restaurantRepository.save(restaurantFound);

        return (RestaurantDTO) DestinationMapper.mapToDestinationDTO(updated);
    }

    @Override
    public void deleteRestaurant(Long idRestaurant) {
        Restaurant restaurantFound = restaurantRepository.findById(idRestaurant).orElseThrow(() -> new RuntimeException("No se encontró el restaurante."));
        restaurantRepository.delete(restaurantFound);
    }

    @Override
    public List<RestaurantDTO> findAllByOrderByAverageScoreDesc() {
        List<Restaurant> restaurants = restaurantRepository.findAllByOrderByAverageScoreDesc();
        return DestinationMapper.genericMapListToTypedDTO(restaurants, RestaurantDTO.class);
    }

    @Override
    public List<RestaurantDTO> dinamicFilterForRestaurants(String locality, Integer minAverageScore, Boolean delivery, Boolean reservations) {
        Specification<Restaurant> specificationRestaurants = Specification.where(RestaurantSpecification.hasLocality(locality))
                .and(RestaurantSpecification.hasAverageScoreGreaterOrEqual(minAverageScore))
                .and(RestaurantSpecification.hasDelivery(delivery))
                .and(RestaurantSpecification.hasReservations(reservations));
        List<Restaurant> restaurants = restaurantRepository.findAll(specificationRestaurants);
        return DestinationMapper.genericMapListToTypedDTO(restaurants, RestaurantDTO.class);
    }

    // Obtener todos los restaurantes
    public List<RestaurantDTO> getAllRestaurants() {
        return findAllRestaurants();
    }

    // Obtener un restaurante por ID
    public RestaurantDTO getRestaurantById(Long idRestaurante) {
        return findRestaurantById(idRestaurante);
    }

    // Crear un restaurante
    public RestaurantDTO createRestaurant(RestaurantDTO restauranteDTO, List<MultipartFile> imagenes) {
        User ownerFound = userRepository.findById(restauranteDTO.getOwnerId()).orElseThrow(() -> new RuntimeException("Propietario no encontrado."));
        Restaurant restaurant = DestinationMapper.mapDtoToEntityForSave(restauranteDTO, Restaurant.class, ownerFound);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        // Si tienes lógica para imágenes, agrégala aquí
        return (RestaurantDTO) DestinationMapper.mapToDestinationDTO(savedRestaurant);
    }

    // Actualizar un restaurante
    // Elimino la versión duplicada para evitar ambigüedad

    // Eliminar un restaurante
    // Elimino la versión duplicada para evitar ambigüedad

    // Listar restaurantes ordenados por puntaje promedio (descendente)
    public List<RestaurantDTO> getRestaurantsByOrderDescendent() {
        List<Restaurant> restaurants = restaurantRepository.findAllByOrderByAverageScoreDesc();
        return DestinationMapper.genericMapListToTypedDTO(restaurants, RestaurantDTO.class);
    }

    // Filtrado dinámico de restaurantes
    public List<RestaurantDTO> dinamicFilterRestaurants(String localidad, Integer puntuacionMinima, Boolean entrega, Boolean reservas) {
        Specification<Restaurant> spec = Specification.where(null);
        if (localidad != null) {
            spec = spec.and(RestaurantSpecification.hasLocality(localidad));
        }
        if (puntuacionMinima != null) {
            spec = spec.and(RestaurantSpecification.hasAverageScoreGreaterOrEqual(puntuacionMinima));
        }
        if (entrega != null) {
            spec = spec.and(RestaurantSpecification.hasDelivery(entrega));
        }
        if (reservas != null) {
            spec = spec.and(RestaurantSpecification.hasReservations(reservas));
        }
        List<Restaurant> restaurants = restaurantRepository.findAll(spec);
        return DestinationMapper.genericMapListToTypedDTO(restaurants, RestaurantDTO.class);
    }
}

