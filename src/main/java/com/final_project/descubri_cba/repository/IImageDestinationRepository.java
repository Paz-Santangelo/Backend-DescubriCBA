package com.final_project.descubri_cba.repository;

import com.final_project.descubri_cba.model.ImageDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageDestinationRepository extends JpaRepository<ImageDestination, Long> {
    List<ImageDestination> findByDestination_Id(Long destinationId);
}
