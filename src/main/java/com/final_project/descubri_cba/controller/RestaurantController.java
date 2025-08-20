package com.final_project.descubri_cba.controller;

import com.final_project.descubri_cba.dto.RestaurantDTO;
import com.final_project.descubri_cba.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("/todos")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<RestaurantDTO> restaurantes = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurantes);
    }


    @GetMapping("/{idRestaurante}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long idRestaurante) {
        RestaurantDTO restaurante = restaurantService.getRestaurantById(idRestaurante);
        if (restaurante == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurante);
    }


    @PostMapping("/nuevo")
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO nuevoRestaurante = restaurantService.createRestaurant(restaurantDTO, null);
        return ResponseEntity.status(201).body(nuevoRestaurante);
    }


    @PutMapping("/actualizar/{idRestaurante}")
    public ResponseEntity<RestaurantDTO> updateRestaurant(
            @PathVariable Long idRestaurante,
            @RequestBody RestaurantDTO restaurantDTO) {
        try {
            RestaurantDTO restauranteActualizado = restaurantService.updateRestaurant(idRestaurante, restaurantDTO, null);
            if (restauranteActualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(restauranteActualizado);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }


    @DeleteMapping("/eliminar/{idRestaurante}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long idRestaurante) {
        try {
            restaurantService.deleteRestaurant(idRestaurante);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
