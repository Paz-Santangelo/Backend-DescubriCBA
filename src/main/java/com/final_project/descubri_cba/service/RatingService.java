package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.RatingDTO;
import com.final_project.descubri_cba.exception.CustomException;
import com.final_project.descubri_cba.model.Destination;
import com.final_project.descubri_cba.model.Rating;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.repository.IDestinationRepository;
import com.final_project.descubri_cba.repository.IRatingRepository;
import com.final_project.descubri_cba.repository.IUserRepository;
import com.final_project.descubri_cba.utils.RatingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService implements IRatingService {

    @Autowired
    private IRatingRepository ratingRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IDestinationRepository destinationRepository;

    @Override
    public RatingDTO saveOrUpdateRating(RatingDTO ratingDTO) {
        User userFound = userRepository.findById(ratingDTO.getIdUser())
                .orElseThrow(() -> new CustomException("Usuario no encontrado.", HttpStatus.NOT_FOUND));

        Destination destinationFound = destinationRepository.findById(ratingDTO.getIdDestination())
                .orElseThrow(() -> new CustomException("Destino no encontrado.", HttpStatus.NOT_FOUND));

        Optional<Rating> qualifiedRating = ratingRepository.findByUserAndDestination(userFound, destinationFound);

        Rating rating;
        if (qualifiedRating.isPresent()) {
            rating = qualifiedRating.get();
            rating.setScore(ratingDTO.getScore());
        } else {
            rating = new Rating();
            rating.setUser(userFound);
            rating.setDestination(destinationFound);
            rating.setScore(ratingDTO.getScore());
        }

        Rating ratingSaved = ratingRepository.save(rating);

        Double averageScore = ratingRepository.findAverageScoreByDestinationId(destinationFound.getId());
        destinationFound.setAverageScore(averageScore != null ? averageScore.intValue() : 0);
        destinationRepository.save(destinationFound);

        return RatingMapper.convertRatingEntityToRatingDTO(ratingSaved);
    }

}
