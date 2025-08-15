package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.BodyOfWaterDTO;
import com.final_project.descubri_cba.enums.TypeBodyOfWater;
import com.final_project.descubri_cba.model.BodyOfWater;
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
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private DestinationMapper destinationMapper;

    @Override
    public List<BodyOfWaterDTO> findAllBodiesOfWater() {
        List<BodyOfWater> bodies = bodyOfWaterRepository.findAll();
        return DestinationMapper.genericMapListToTypedDTO(bodies, BodyOfWaterDTO.class);
    }

    public BodyOfWaterDTO findBodyOfWaterById(Long idBody) {
        Optional<BodyOfWater> bodyOpt = bodyOfWaterRepository.findById(idBody);
        if (bodyOpt.isEmpty()) throw new RuntimeException("Cuerpo de agua no encontrado");
        return DestinationMapper.genericMapToTypedDTO(bodyOpt.get(), BodyOfWaterDTO.class);
    }

    @Override
    @Transactional
    public BodyOfWaterDTO saveBodyOfWater(List<MultipartFile> files, BodyOfWaterDTO bodyOfWaterDTO) {
        Optional<User> ownerOpt = userRepository.findById(bodyOfWaterDTO.getOwnerId());
        if (ownerOpt.isEmpty()) throw new RuntimeException("Propietario no encontrado");

        BodyOfWater body = DestinationMapper.mapDtoToEntityForSave(bodyOfWaterDTO, BodyOfWater.class, ownerOpt.get());
        bodyOfWaterRepository.save(body);

        if (files != null && !files.isEmpty()) {
            try {
                imageService.uploadImagesDestinations(files, body);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir imágenes", e);
            }
            bodyOfWaterRepository.save(body);
        }

        return DestinationMapper.genericMapToTypedDTO(body, BodyOfWaterDTO.class);
    }

    @Override
    @Transactional
    public BodyOfWaterDTO updateBodyOfWater(Long idBody, List<MultipartFile> files, BodyOfWaterDTO bodyOfWaterDTO) {
        Optional<BodyOfWater> bodyOpt = bodyOfWaterRepository.findById(idBody);
        if (bodyOpt.isEmpty()) throw new RuntimeException("Cuerpo de agua no encontrado");

        Optional<User> ownerOpt = userRepository.findById(bodyOfWaterDTO.getOwnerId());
        if (ownerOpt.isEmpty()) throw new RuntimeException("Propietario no encontrado");

        BodyOfWater body = bodyOpt.get();

        if (files != null && !files.isEmpty()) {
            for (var image : body.getImagesDestinations()) {
                try {
                    imageService.deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
                } catch (IOException e) {
                    throw new RuntimeException("Error al eliminar imágenes", e);
                }
            }
            try {
                imageService.uploadImagesDestinations(files, body);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir imágenes", e);
            }
        }

        DestinationMapper.mapDtoToEntityForUpdate(bodyOfWaterDTO, body, ownerOpt.get());
        bodyOfWaterRepository.save(body);

        return DestinationMapper.genericMapToTypedDTO(body, BodyOfWaterDTO.class);
    }

    @Override
    @Transactional
    public void deleteBodyOfWater(Long idBody) {
        Optional<BodyOfWater> bodyOpt = bodyOfWaterRepository.findById(idBody);
        if (bodyOpt.isEmpty()) throw new RuntimeException("Cuerpo de agua no encontrado");

        BodyOfWater body = bodyOpt.get();

        for (var image : body.getImagesDestinations()) {
            try {
                imageService.deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
            } catch (IOException e) {
                throw new RuntimeException("Error al eliminar imágenes", e);
            }
        }

        bodyOfWaterRepository.deleteById(idBody);
    }

    public List<BodyOfWaterDTO> findAllByOrderByAverageScoreDesc() {
        List<BodyOfWater> bodies = bodyOfWaterRepository.findAllByOrderByAverageScoreDesc();
        return DestinationMapper.genericMapListToTypedDTO(bodies, BodyOfWaterDTO.class);
    }

    @Override
    public List<BodyOfWaterDTO> dinamicFilterForBodyOfWater(String locality, Integer minAverageScore, Boolean freeAdmission, TypeBodyOfWater type) {
        Specification<BodyOfWater> spec = Specification.where(
                BodyOfWaterSpecification.hasLocality(locality)
        ).and(
                BodyOfWaterSpecification.hasAverageScoreGreaterOrEqual(minAverageScore)
        ).and(
                BodyOfWaterSpecification.hasFreeAdmission(freeAdmission)
        ).and(
                BodyOfWaterSpecification.hasType(type)
        );

        List<BodyOfWater> bodies = bodyOfWaterRepository.findAll(spec);
        return DestinationMapper.genericMapListToTypedDTO(bodies, BodyOfWaterDTO.class);
    }
}

