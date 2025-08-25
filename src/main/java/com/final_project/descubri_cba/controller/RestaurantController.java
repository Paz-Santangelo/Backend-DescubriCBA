package com.final_project.descubri_cba.controller;

import com.final_project.descubri_cba.dto.RestaurantDTO;
import com.final_project.descubri_cba.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/all")
    public List<RestaurantDTO> findAllRestaurants() {
        return restaurantService.findAllRestaurants();
    }

    @GetMapping("/{idRestaurant}")
    public RestaurantDTO findRestaurantById(@PathVariable Long idRestaurant) {
        return restaurantService.findRestaurantById(idRestaurant);
    }

    @PostMapping("/create")
    public ResponseEntity<RestaurantDTO> createRestaurant(
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @Valid @ModelAttribute RestaurantDTO restaurantDTO) {

        RestaurantDTO created = restaurantService.saveRestaurant(files, restaurantDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{idRestaurant}")
    public ResponseEntity<RestaurantDTO> updateRestaurant(@PathVariable Long idRestaurant,
                                                          @RequestParam(value = "files", required = false) List<MultipartFile> files,
                                                          @Valid @ModelAttribute RestaurantDTO restaurantDTO) throws IOException {
        RestaurantDTO restaurantUpdated = restaurantService.updateRestaurant(idRestaurant, files, restaurantDTO);
        return ResponseEntity.ok(restaurantUpdated);
    }

    @DeleteMapping("/delete/{idRestaurant}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long idRestaurant) {
        restaurantService.deleteRestaurant(idRestaurant);
        return ResponseEntity.ok("El restaurante fue eliminado con éxito.");
    }

    @GetMapping("/allByOrderDescendent")
    public ResponseEntity<?> findAllRestaurantsByOrderDescendent() {
        List<RestaurantDTO> restaurants = restaurantService.findAllByOrderByAverageScoreDesc();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/dinamicFilter")
    public List<RestaurantDTO> dinamicFilterForRestaurants(@RequestParam(required = false) String locality,
                                                           @RequestParam(required = false) Integer minAverageScore,
                                                           @RequestParam(required = false) Boolean delivery,
                                                           @RequestParam(required = false) Boolean reservations) {
        return restaurantService.dinamicFilterForRestaurants(locality, minAverageScore, delivery, reservations);
    }
}