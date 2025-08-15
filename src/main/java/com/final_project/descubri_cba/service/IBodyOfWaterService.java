package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.BodyOfWaterDTO;
import com.final_project.descubri_cba.enums.TypeBodyOfWater;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBodyOfWaterService {
    List<BodyOfWaterDTO> findAllBodiesOfWater();

    BodyOfWaterDTO saveBodyOfWater(List<MultipartFile> files, BodyOfWaterDTO bodyOfWaterDto);

    BodyOfWaterDTO updateBodyOfWater(Long idBody, List<MultipartFile> files, BodyOfWaterDTO bodyOfWaterDto);

    void deleteBodyOfWater(Long idBody);

    List<BodyOfWaterDTO> dinamicFilterForBodyOfWater(
            String locality,
            Integer minAverageScore,
            Boolean freeAdmission,
            TypeBodyOfWater type
    );
}
