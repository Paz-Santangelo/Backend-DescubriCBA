package com.final_project.descubri_cba.repository;

import com.final_project.descubri_cba.model.EmergencyServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmergencyServicesRepository  extends JpaRepository<EmergencyServices, Long> {
}
