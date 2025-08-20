package com.final_project.descubri_cba.controller;

import com.final_project.descubri_cba.dto.RatingDTO;
import com.final_project.descubri_cba.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calificaciones")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/guardarActualizar")
    public ResponseEntity<RatingDTO> saveOrUpdateRating(@RequestBody RatingDTO ratingDTO) {
        Long idDestination = ratingDTO.getIdDestination();
        Long idUser = ratingDTO.getIdUser();
        RatingDTO resultado = ratingService.saveOrUpdateRating(ratingDTO, idDestination, idUser);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/puntuacion/{idDestination}")
    public ResponseEntity<Double> getRatingByDestination(@PathVariable Long idDestination) {
        Double puntuacion = ratingService.getRatingByDestination(idDestination);
        return ResponseEntity.ok(puntuacion);
    }
}