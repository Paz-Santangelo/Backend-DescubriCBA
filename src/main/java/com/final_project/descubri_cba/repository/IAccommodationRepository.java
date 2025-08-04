package com.final_project.descubri_cba.repository;

import com.final_project.descubri_cba.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccommodationRepository extends JpaRepository<Accommodation, Long> {

}


