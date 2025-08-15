package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.RestaurantDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface IRestaurantService {
    List<RestaurantDTO> findAllRestaurants();
    RestaurantDTO findRestaurantById(Long id);
    RestaurantDTO saveRestaurant(RestaurantDTO restaurantDTO, List<MultipartFile> files);
    RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO, List<MultipartFile> files);
    void deleteRestaurant(Long id);
    List<RestaurantDTO> findAllByOrderByAverageScoreDesc();
    List<RestaurantDTO> dinamicFilterForRestaurants(String localidad, Integer minAverageScore, Boolean entrega, Boolean reservas);
}
