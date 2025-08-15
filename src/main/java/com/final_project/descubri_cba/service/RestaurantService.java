package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.RestaurantDTO;
import com.final_project.descubri_cba.model.Restaurant;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.repository.IRestaurantRepository;
import com.final_project.descubri_cba.repository.IUserRepository;
import com.final_project.descubri_cba.repository.IImageDestinationRepository;
import com.final_project.descubri_cba.service.ImageService;
import com.final_project.descubri_cba.service.IRestaurantService;
import com.final_project.descubri_cba.utils.DestinationMapper;
import com.final_project.descubri_cba.specification.RestaurantSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
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
    private DestinationMapper destinationMapper;
    @Autowired
    private IImageDestinationRepository imageDestinationRepository;

    @Override
    public List<RestaurantDTO> findAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return DestinationMapper.genericMapListToTypedDTO(restaurants, RestaurantDTO.class);
    }

    @Override
    public RestaurantDTO findRestaurantById(Long id) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(id);
        if (restaurantOpt.isEmpty()) throw new RuntimeException("Restaurante no encontrado");
        return DestinationMapper.genericMapToTypedDTO(restaurantOpt.get(), RestaurantDTO.class);
    }

    @Override
    @Transactional
    public RestaurantDTO saveRestaurant(RestaurantDTO restaurantDTO, List<MultipartFile> files) {
        Optional<User> ownerOpt = userRepository.findById(restaurantDTO.getOwnerId());
        if (ownerOpt.isEmpty()) throw new RuntimeException("Propietario no encontrado");
        Restaurant restaurant = DestinationMapper.mapDtoToEntityForSave(restaurantDTO, ownerOpt.get(), Restaurant.class);
        restaurantRepository.save(restaurant);
        if (files != null && !files.isEmpty()) {
            try {
                imageService.uploadImagesDestinations(files, restaurant);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir imágenes", e);
            }
            restaurantRepository.save(restaurant);
        }
        return DestinationMapper.genericMapToTypedDTO(restaurant, RestaurantDTO.class);
    }

    @Override
    @Transactional
    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO, List<MultipartFile> files) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(id);
        if (restaurantOpt.isEmpty()) throw new RuntimeException("Restaurante no encontrado");
        Optional<User> ownerOpt = userRepository.findById(restaurantDTO.getOwnerId());
        if (ownerOpt.isEmpty()) throw new RuntimeException("Propietario no encontrado");
        Restaurant restaurant = restaurantOpt.get();
        if (files != null && !files.isEmpty()) {
            for (var image : restaurant.getImagesDestinations()) {
                try {
                    imageService.deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
                } catch (IOException e) {
                    throw new RuntimeException("Error al eliminar imágenes", e);
                }
            }
            try {
                imageService.uploadImagesDestinations(files, restaurant);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir imágenes", e);
            }
        }
        DestinationMapper.mapDtoToEntityForUpdate(restaurantDTO, restaurant, ownerOpt.get());
        restaurantRepository.save(restaurant);
        return DestinationMapper.genericMapToTypedDTO(restaurant, RestaurantDTO.class);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long id) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(id);
        if (restaurantOpt.isEmpty()) throw new RuntimeException("Restaurante no encontrado");
        Restaurant restaurant = restaurantOpt.get();
        for (var image : restaurant.getImagesDestinations()) {
            try {
                imageService.deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
            } catch (IOException e) {
                throw new RuntimeException("Error al eliminar imágenes", e);
            }
        }
        restaurantRepository.deleteById(id);
    }

    @Override
    public List<RestaurantDTO> findAllByOrderByAverageScoreDesc() {
        List<Restaurant> restaurants = restaurantRepository.findAllByOrderByAverageScoreDesc();
        return DestinationMapper.genericMapListToTypedDTO(restaurants, RestaurantDTO.class);
    }

    @Override
    public List<RestaurantDTO> dinamicFilterForRestaurants(String localidad, Integer minAverageScore, Boolean entrega, Boolean reservas) {
        var spec = RestaurantSpecification.buildSpecification(localidad, minAverageScore, entrega, reservas);
        List<Restaurant> restaurants = restaurantRepository.findAll(spec);
        return DestinationMapper.genericMapListToTypedDTO(restaurants, RestaurantDTO.class);
    }
}
