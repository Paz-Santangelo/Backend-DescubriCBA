package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.RatingDTO;

public interface IRatingService {
    public RatingDTO saveOrUpdateRating(RatingDTO ratingDTO);
}