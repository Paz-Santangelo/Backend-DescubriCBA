package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.RestaurantDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IRestaurantService {
    List<RestaurantDTO> findAllRestaurants();

    RestaurantDTO findRestaurantById(Long idRestaurant);

    RestaurantDTO saveRestaurant(RestaurantDTO restaurantDTO, List<MultipartFile> files);

    RestaurantDTO updateRestaurant(Long idRestaurant, RestaurantDTO restaurantDTO, List<MultipartFile> files) throws IOException;

    void deleteRestaurant(Long idRestaurant);

    List<RestaurantDTO> findAllByOrderByAverageScoreDesc();

    List<RestaurantDTO> dinamicFilterForRestaurants(String locality, Integer minAverageScore, Boolean delivery, Boolean reservations);
}
