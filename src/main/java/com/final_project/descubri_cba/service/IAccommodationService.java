package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.AccommodationDTO;
import com.final_project.descubri_cba.enums.AccommodationType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAccommodationService {

    List<AccommodationDTO> findAllAccommodations();

    AccommodationDTO findAccommodationById(Long idAccommodation);

    List<AccommodationDTO> findAllByOrderByAverageScoreDesc();

    AccommodationDTO saveAccommodation(List<MultipartFile> files, AccommodationDTO accommodationDto);

    AccommodationDTO updateAccommodation(Long idAccommodation, List<MultipartFile> files, AccommodationDTO accommodationDto);

    void deleteAccommodation(Long idAccommodation);

    List<AccommodationDTO> dinamicFilterForAccommodation(String locality, Integer minAverageScore, AccommodationType type);
}
