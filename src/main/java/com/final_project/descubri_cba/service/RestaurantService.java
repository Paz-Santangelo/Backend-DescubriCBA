package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.RestaurantDTO;
import com.final_project.descubri_cba.dto.UserDTO;
import com.final_project.descubri_cba.model.ImageDestination;
import com.final_project.descubri_cba.model.Restaurant;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.repository.IImageDestinationRepository;
import com.final_project.descubri_cba.repository.IRestaurantRepository;
import com.final_project.descubri_cba.repository.IUserRepository;
import com.final_project.descubri_cba.specification.RestaurantSpecification;
import com.final_project.descubri_cba.utils.DestinationMapper;
import com.final_project.descubri_cba.utils.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService implements IRestaurantService {

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Autowired
    private IImageDestinationRepository imageDestinationRepository;

    @Autowired
    private IImageService imageService;

    @Autowired
    private IUserRepository userRepository;

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
    public RestaurantDTO saveRestaurant(List<MultipartFile> files, RestaurantDTO restaurantDTO) {
        try {
            // Buscar al propietario ya registrado y guardarlo en esta variable ownerFound
            User ownerFound = userRepository.findById(restaurantDTO.getOwnerId()).orElseThrow(() -> new RuntimeException("No se encontró el propietario."));

            // Usar el mapper para convertir el restaurantDTO en una entidad, necesaria para guardarla en BD. Mandamos el restaurantDTO, el tipo de clase que queremos que se convierta, que seria Restaurant
            Restaurant restaurant = DestinationMapper.mapDtoToEntityForSave(restaurantDTO, Restaurant.class, ownerFound);

            // Guardar restaurante base
            Restaurant restaurantSaved = restaurantRepository.save(restaurant);

            // Cargar imágenes si las hay
            if (files != null && !files.isEmpty()) {
                List<ImageDestination> images = imageService.uploadImagesDestinations(files, restaurantSaved);
                restaurantSaved.setImagesDestinations(images);
                restaurantSaved = restaurantRepository.save(restaurantSaved);
            }

            return (RestaurantDTO) DestinationMapper.mapToDestinationDTO(restaurantSaved);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el restaurante");
        }
    }

    @Override
    @Transactional
    public RestaurantDTO updateRestaurant(Long idRestaurant, List<MultipartFile> files, RestaurantDTO restaurantDTO) throws IOException {

        // Buscamos el restaurante en la BD que debemos actualizar.
        Restaurant restaurantFound = restaurantRepository.findById(idRestaurant)
                .orElseThrow(() -> new RuntimeException("No se encontró el restaurante"));

        // Buscar al propietario ya registrado y guardarlo en esta variable ownerFound
        User ownerFound = userRepository.findById(restaurantDTO.getOwnerId()).orElseThrow(() -> new RuntimeException("No se encontró el propietario."));

        // Actualización de imágenes
        if (files != null && !files.isEmpty()) {
            // Eliminar imágenes existentes
            List<ImageDestination> existingImages = new ArrayList<>(restaurantFound.getImagesDestinations());
            for (ImageDestination image : existingImages) {
                restaurantFound.removeImageDestination(image);
                imageService.deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
            }

            // Subir nuevas imágenes
            List<ImageDestination> newImages = imageService.uploadImagesDestinations(files, restaurantFound);
            for (ImageDestination image : newImages) {
                restaurantFound.addImageDestination(image);
            }
        }

        // Mandamos todos los datos del restaurante a actualizar al convertidor para que lo convierta a una entidad.
        DestinationMapper.mapDtoToEntityForUpdate(restaurantDTO, restaurantFound, ownerFound);

        // Guardar cambios
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
}
