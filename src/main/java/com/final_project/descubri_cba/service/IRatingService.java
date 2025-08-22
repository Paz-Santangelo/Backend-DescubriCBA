package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.RatingDTO;
import com.final_project.descubri_cba.model.Destination;
import com.final_project.descubri_cba.model.Rating;
import com.final_project.descubri_cba.model.User;

import java.util.List;

public interface IRatingService {
    public RatingDTO saveOrUpdateRating(RatingDTO ratingDTO);
}