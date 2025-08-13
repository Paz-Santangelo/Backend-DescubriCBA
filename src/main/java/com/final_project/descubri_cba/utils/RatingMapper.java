package com.final_project.descubri_cba.utils;

import com.final_project.descubri_cba.dto.RatingDTO;
import com.final_project.descubri_cba.model.Rating;

import java.util.List;

public class RatingMapper {
    public static RatingDTO convertRatingEntityToRatingDTO(Rating rating) {
        return new RatingDTO(
                rating.getScore(),
                rating.getUser().getId(),
                rating.getDestination().getId(),
                rating.getDestination().getName()
        );
    }

    public static List<RatingDTO> convertRatingEntityListToRatingDTOList(List<Rating> ratings) {
        return ratings.stream()
                .map(RatingMapper::convertRatingEntityToRatingDTO)
                .toList();
    }
}
