package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.EmergencyServicesDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEmergencyServicesService {

    List<EmergencyServicesDTO> findAllEmergencyServices();

    EmergencyServicesDTO findEmergencyServiceById(Long idEmergency);

    List<EmergencyServicesDTO> findAllByOrderByAverageScoreDesc();

    EmergencyServicesDTO saveEmergencyServices(List<MultipartFile> files, EmergencyServicesDTO emergencyServicesDto);

    EmergencyServicesDTO updateEmergencyServices(Long idEmergency, List<MultipartFile> files, EmergencyServicesDTO emergencyServicesDto) throws IOException;

    void deleteEmergencyServices(Long idEmergency);

    List<EmergencyServicesDTO> dinamicFilterForEmergencyServices(
            String locality,
            Integer minAverageScore,
            String type
    );
}
