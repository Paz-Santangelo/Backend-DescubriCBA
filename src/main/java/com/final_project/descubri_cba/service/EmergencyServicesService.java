package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.EmergencyServicesDTO;
import com.final_project.descubri_cba.enums.TypeOfEmergency;
import com.final_project.descubri_cba.exception.CustomException;
import com.final_project.descubri_cba.model.EmergencyServices;
import com.final_project.descubri_cba.model.ImageDestination;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.repository.IEmergencyServicesRepository;
import com.final_project.descubri_cba.repository.IUserRepository;
import com.final_project.descubri_cba.repository.IImageDestinationRepository;
import com.final_project.descubri_cba.specification.EmergencyServiceSpecification;
import com.final_project.descubri_cba.utils.DestinationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmergencyServicesService implements IEmergencyServicesService {

    @Autowired
    private IEmergencyServicesRepository emergencyServicesRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IImageService imageService;

    @Autowired
    private IImageDestinationRepository imageDestinationRepository;
    
    @Override
    public List<EmergencyServicesDTO> findAllEmergencyServices() {
        List<EmergencyServices> services = emergencyServicesRepository.findAll();
        return DestinationMapper.genericMapListToTypedDTO(services, EmergencyServicesDTO.class);
    }

    @Override
    public EmergencyServicesDTO findEmergencyServiceById(Long idEmergency) {
        EmergencyServices emergencyFound = emergencyServicesRepository.findById(idEmergency)
                .orElseThrow(() -> new CustomException("Servicio de emergencia no encontrado.", HttpStatus.NOT_FOUND));
        return (EmergencyServicesDTO) DestinationMapper.mapToDestinationDTO(emergencyFound);
    }

    @Override
    @Transactional
    public EmergencyServicesDTO saveEmergencyServices(List<MultipartFile> files, EmergencyServicesDTO emergencyServicesDTO) throws IOException {
        User ownerFound = userRepository.findById(emergencyServicesDTO.getOwnerId())
                .orElseThrow(() -> new CustomException("Propietario no encontrado.", HttpStatus.NOT_FOUND));

        EmergencyServices service = DestinationMapper.mapDtoToEntityForSave(emergencyServicesDTO, EmergencyServices.class, ownerFound);

        EmergencyServices emergencySaved = emergencyServicesRepository.save(service);

        if (files != null && !files.isEmpty()) {
            List<ImageDestination> images = imageService.uploadImagesDestinations(files, emergencySaved);
            emergencySaved.setImagesDestinations(images);
            emergencySaved = emergencyServicesRepository.save(emergencySaved);
        }

        return (EmergencyServicesDTO) DestinationMapper.mapToDestinationDTO(emergencySaved);
    }

    @Override
    @Transactional
    public EmergencyServicesDTO updateEmergencyServices(Long idEmergency, List<MultipartFile> files, EmergencyServicesDTO emergencyServicesDTO) throws IOException {
        EmergencyServices emergencyFound = emergencyServicesRepository.findById(idEmergency)
                .orElseThrow(() -> new CustomException("Servicio de emergencia no encontrado.", HttpStatus.NOT_FOUND));

        User ownerFound = userRepository.findById(emergencyServicesDTO.getOwnerId())
                .orElseThrow(() -> new CustomException("Propietario no encontrado.", HttpStatus.NOT_FOUND));

        if (files != null && !files.isEmpty()) {
            List<ImageDestination> existingImages = new ArrayList<>(emergencyFound.getImagesDestinations());
            for (ImageDestination image : existingImages) {
                emergencyFound.removeImageDestination(image);
                imageService.deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
            }

            List<ImageDestination> newImages = imageService.uploadImagesDestinations(files, emergencyFound);
            for (ImageDestination image : newImages) {
                emergencyFound.addImageDestination(image);
            }
        }

        DestinationMapper.mapDtoToEntityForUpdate(emergencyServicesDTO, emergencyFound, ownerFound);
        emergencyServicesRepository.save(emergencyFound);

        return (EmergencyServicesDTO) DestinationMapper.mapToDestinationDTO(emergencyFound);
    }

    @Override
    public void deleteEmergencyServices(Long idEmergency) {
        EmergencyServices emergencyFound = emergencyServicesRepository.findById(idEmergency)
                .orElseThrow(() -> new CustomException("Servicio de emergencia no encontrado.", HttpStatus.NOT_FOUND));
        emergencyServicesRepository.delete(emergencyFound);
    }

    @Override
    public List<EmergencyServicesDTO> findAllByOrderByAverageScoreDesc() {
        List<EmergencyServices> services = emergencyServicesRepository.findAllByOrderByAverageScoreDesc();
        return DestinationMapper.genericMapListToTypedDTO(services, EmergencyServicesDTO.class);
    }

    @Override
    public List<EmergencyServicesDTO> dinamicFilterForEmergencyServices(String locality, Integer minAverageScore, String type) {
        TypeOfEmergency typeEmergency = type != null ? TypeOfEmergency.fromString(type) : null;

        Specification<EmergencyServices> spec = Specification.where(
                EmergencyServiceSpecification.hasLocality(locality)
        ).and(
                EmergencyServiceSpecification.hasAverageScoreGreaterOrEqual(minAverageScore)
        ).and(
                EmergencyServiceSpecification.hasType(typeEmergency)
        );

        List<EmergencyServices> services = emergencyServicesRepository.findAll(spec);
        return DestinationMapper.genericMapListToTypedDTO(services, EmergencyServicesDTO.class);
    }
}
