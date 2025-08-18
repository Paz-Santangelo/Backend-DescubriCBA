package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.BodyOfWaterDTO;
import com.final_project.descubri_cba.enums.TypeBodyOfWater;
import com.final_project.descubri_cba.model.BodyOfWater;
import com.final_project.descubri_cba.model.ImageDestination;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.repository.IBodyOfWaterRepository;
import com.final_project.descubri_cba.repository.IImageDestinationRepository;
import com.final_project.descubri_cba.repository.IUserRepository;
import com.final_project.descubri_cba.specification.BodyOfWaterSpecification;
import com.final_project.descubri_cba.utils.DestinationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BodyOfWaterService implements IBodyOfWaterService {
    @Autowired
    private IBodyOfWaterRepository bodyOfWaterRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IImageService imageService;

    @Autowired
    private IImageDestinationRepository imageDestinationRepository;

    @Override
    public List<BodyOfWaterDTO> findAllBodiesOfWater() {
        List<BodyOfWater> bodies = bodyOfWaterRepository.findAll();
        return DestinationMapper.genericMapListToTypedDTO(bodies, BodyOfWaterDTO.class);
    }

    @Override
    public BodyOfWaterDTO findBodyOfWaterById(Long idBody) {
        BodyOfWater bodyFound = bodyOfWaterRepository.findById(idBody).orElseThrow(() -> new RuntimeException("Cuerpo de agua no encontrado"));
        return (BodyOfWaterDTO) DestinationMapper.mapToDestinationDTO(bodyFound);
    }

    @Override
    @Transactional
    public BodyOfWaterDTO saveBodyOfWater(List<MultipartFile> files, BodyOfWaterDTO bodyOfWaterDTO) {
        try {
            User userFound = userRepository.findById(bodyOfWaterDTO.getOwnerId()).orElseThrow(() -> new RuntimeException("Propietario no encontrado"));

            BodyOfWater body = DestinationMapper.mapDtoToEntityForSave(bodyOfWaterDTO, BodyOfWater.class, userFound);

            BodyOfWater bodySaved = bodyOfWaterRepository.save(body);

            if (files != null && !files.isEmpty()) {
                List<ImageDestination> images = imageService.uploadImagesDestinations(files, bodySaved);
                bodySaved.setImagesDestinations(images);
                bodySaved = bodyOfWaterRepository.save(bodySaved);
            }

            return (BodyOfWaterDTO) DestinationMapper.mapToDestinationDTO(bodySaved);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el cuerpo de agua: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public BodyOfWaterDTO updateBodyOfWater(Long idBody, List<MultipartFile> files, BodyOfWaterDTO bodyOfWaterDTO) throws IOException {
        BodyOfWater bodyFound = bodyOfWaterRepository.findById(idBody).orElseThrow(() -> new RuntimeException("Cuerpo de agua no encontrado"));

        User ownerFound = userRepository.findById(bodyOfWaterDTO.getOwnerId()).orElseThrow(() -> new RuntimeException("Propietario no encontrado"));

        if (files != null && !files.isEmpty()) {
            List<ImageDestination> existingImages = new ArrayList<>(bodyFound.getImagesDestinations());
            for (ImageDestination image : existingImages) {
                bodyFound.removeImageDestination(image);
                imageService.deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
            }

            List<ImageDestination> newImages = imageService.uploadImagesDestinations(files, bodyFound);
            for (ImageDestination image : newImages) {
                bodyFound.addImageDestination(image);
            }
        }

        DestinationMapper.mapDtoToEntityForUpdate(bodyOfWaterDTO, bodyFound, ownerFound);
        BodyOfWater bodyUpdated = bodyOfWaterRepository.save(bodyFound);

        return (BodyOfWaterDTO) DestinationMapper.mapToDestinationDTO(bodyUpdated);
    }

    @Override
    public void deleteBodyOfWater(Long idBody) {
        BodyOfWater bodyFound = bodyOfWaterRepository.findById(idBody).orElseThrow(() -> new RuntimeException("Cuerpo de agua no encontrado"));
        bodyOfWaterRepository.delete(bodyFound);
    }

    @Override
    public List<BodyOfWaterDTO> findAllByOrderByAverageScoreDesc() {
        List<BodyOfWater> bodies = bodyOfWaterRepository.findAllByOrderByAverageScoreDesc();
        return DestinationMapper.genericMapListToTypedDTO(bodies, BodyOfWaterDTO.class);
    }

    @Override
    public List<BodyOfWaterDTO> dinamicFilterForBodyOfWater(String locality, Integer minAverageScore, Boolean freeAdmission, String type) {
        TypeBodyOfWater typeBody = type != null ? TypeBodyOfWater.fromString(type) : null;


        Specification<BodyOfWater> spec = Specification.where(
                BodyOfWaterSpecification.hasLocality(locality)
        ).and(
                BodyOfWaterSpecification.hasAverageScoreGreaterOrEqual(minAverageScore)
        ).and(
                BodyOfWaterSpecification.hasFreeAdmission(freeAdmission)
        ).and(
                BodyOfWaterSpecification.hasType(typeBody)
        );

        List<BodyOfWater> bodies = bodyOfWaterRepository.findAll(spec);
        return DestinationMapper.genericMapListToTypedDTO(bodies, BodyOfWaterDTO.class);
    }
}

