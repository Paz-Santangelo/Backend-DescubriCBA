package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.RatingDTO;

public interface IRatingService {
    /**
     * Registra o actualiza la puntuación de un destino para un usuario.
     * @param ratingDTO DTO con la información de la puntuación
     * @param destinationId ID del destino
     * @param userId ID del usuario
     * @return RatingDTO actualizado o registrado
     */
    RatingDTO saveOrUpdateRating(RatingDTO ratingDTO, Long destinationId, Long userId);
}