package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.AccommodationDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IAccommodationService {

    List<AccommodationDTO> findAllAccommodations();

    AccommodationDTO findAccommodationById(Long idAccommodation);

    List<AccommodationDTO> findAllByOrderByAverageScoreDesc();

    AccommodationDTO saveAccommodation(List<MultipartFile> files, AccommodationDTO accommodationDto) throws IOException;

    AccommodationDTO updateAccommodation(Long idAccommodation, List<MultipartFile> files, AccommodationDTO accommodationDto) throws IOException;

    void deleteAccommodation(Long idAccommodation);

    List<AccommodationDTO> dinamicFilterForAccommodation(String locality, Integer minAverageScore, String type);
}
