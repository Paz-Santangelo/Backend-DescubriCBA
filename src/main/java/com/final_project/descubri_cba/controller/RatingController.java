package com.final_project.descubri_cba.controller;

import com.final_project.descubri_cba.dto.RatingDTO;
import com.final_project.descubri_cba.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/saveUpdate")
    public ResponseEntity<?> saveOrUpdateRating(@RequestBody RatingDTO ratingDTO) {
        RatingDTO ratingSaved = ratingService.saveOrUpdateRating(ratingDTO);
        return ResponseEntity.ok(ratingSaved);
    }
}