package com.final_project.descubri_cba.repository;

import com.final_project.descubri_cba.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByDestinationIdAndScore(Long destinationId, int score);
}
