package com.final_project.descubri_cba.repository;

import com.final_project.descubri_cba.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.destination.id = :destinationId")
    Double findAverageScoreByDestinationId(@Param("destinationId") Long id);
}
