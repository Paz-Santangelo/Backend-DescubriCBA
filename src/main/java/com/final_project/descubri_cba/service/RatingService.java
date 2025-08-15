package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.RatingDTO;
import com.final_project.descubri_cba.model.Destination;
import com.final_project.descubri_cba.model.Rating;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.repository.IDestinationRepository;
import com.final_project.descubri_cba.repository.IRatingRepository;
import com.final_project.descubri_cba.repository.IUserRepository;
import com.final_project.descubri_cba.utils.RatingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public RatingDTO saveOrUpdateRating(RatingDTO ratingDTO, Long destinationId, Long userId) {
        // Validar usuario
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        // Validar destino
        Optional<Destination> destOpt = destinationRepository.findById(destinationId);
        if (destOpt.isEmpty()) {
            throw new RuntimeException("Destino no encontrado");
        }
        User user = userOpt.get();
        Destination destination = destOpt.get();
        // Buscar rating existente
        Optional<Rating> ratingOpt = ratingRepository.findByUserAndDestination(user, destination);
        Rating rating;
        if (ratingOpt.isPresent()) {
            rating = ratingOpt.get();
            rating.setScore(ratingDTO.getScore());
        } else {
            rating = new Rating();
            rating.setUser(user);
            rating.setDestination(destination);
            rating.setScore(ratingDTO.getScore());
        }
        ratingRepository.save(rating);
        // Recalcular promedio
        Double avg = ratingRepository.findAverageScoreByDestinationId(destinationId);
        destination.setAverageScore(avg != null ? avg : 0);
        destinationRepository.save(destination);
        // Convertir a DTO
        return RatingMapper.convertRatingEntityToRatingDTO(rating);
    }
}
