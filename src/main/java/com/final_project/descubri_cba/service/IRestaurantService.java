package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.RestaurantDTO;
import com.final_project.descubri_cba.model.Restaurant;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IRestaurantService {
    public List<RestaurantDTO> findAllRestaurants();

    public RestaurantDTO findRestaurantById(Long idRestaurant);

    public RestaurantDTO saveRestaurant(List<MultipartFile> files, RestaurantDTO restaurantDTO) throws IOException;

    public RestaurantDTO updateRestaurant(Long idRestaurant, List<MultipartFile> files, RestaurantDTO restaurantDTO) throws IOException;

    public void deleteRestaurant(Long idRestaurant);

    List<RestaurantDTO> findAllByOrderByAverageScoreDesc();

    List<RestaurantDTO> dinamicFilterForRestaurants(String locality, Integer minAverageScore, Boolean delivery, Boolean reservations);
}
