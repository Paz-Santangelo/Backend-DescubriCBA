package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.BodyOfWaterDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IBodyOfWaterService {
    List<BodyOfWaterDTO> findAllBodiesOfWater();

    BodyOfWaterDTO findBodyOfWaterById(Long idBody);

    BodyOfWaterDTO saveBodyOfWater(List<MultipartFile> files, BodyOfWaterDTO bodyOfWaterDto) throws IOException;

    BodyOfWaterDTO updateBodyOfWater(Long idBody, List<MultipartFile> files, BodyOfWaterDTO bodyOfWaterDto) throws IOException;

    void deleteBodyOfWater(Long idBody);

    List<BodyOfWaterDTO> dinamicFilterForBodyOfWater(
            String locality,
            Integer minAverageScore,
            Boolean freeAdmission,
            String type
    );

    List<BodyOfWaterDTO> findAllByOrderByAverageScoreDesc();
}
