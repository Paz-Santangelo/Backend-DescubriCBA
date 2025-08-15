package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.EmergencyServicesDTO;
import com.final_project.descubri_cba.enums.TypeOfEmergency;
import com.final_project.descubri_cba.model.EmergencyServices;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.repository.IEmergencyServicesRepository;
import com.final_project.descubri_cba.repository.IUserRepository;
import com.final_project.descubri_cba.repository.IImageDestinationRepository;
import com.final_project.descubri_cba.service.IEmergencyServicesService;
import com.final_project.descubri_cba.service.IImageService;
import com.final_project.descubri_cba.specification.EmergencyServiceSpecification;
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
public class EmergencyServicesService implements IEmergencyServicesService {

    @Autowired
    private IEmergencyServicesRepository emergencyServicesRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IImageService imageService;

    @Autowired
    private IImageDestinationRepository imageDestinationRepository;

    @Autowired
    private DestinationMapper destinationMapper;

    @Override
    public List<EmergencyServicesDTO> findAllEmergencyServices() {
        List<EmergencyServices> services = emergencyServicesRepository.findAll();
        return DestinationMapper.genericMapListToTypedDTO(services, EmergencyServicesDTO.class);
    }

    @Override
    public EmergencyServicesDTO findEmergencyServiceById(Long idEmergency) {
        Optional<EmergencyServices> serviceOpt = emergencyServicesRepository.findById(idEmergency);
        if (serviceOpt.isEmpty()) throw new RuntimeException("Servicio de emergencia no encontrado");
        return DestinationMapper.genericMapToTypedDTO(serviceOpt.get(), EmergencyServicesDTO.class);
    }

    @Override
    @Transactional
    public EmergencyServicesDTO saveEmergencyServices(List<MultipartFile> files, EmergencyServicesDTO emergencyServicesDTO) {
        Optional<User> ownerOpt = userRepository.findById(emergencyServicesDTO.getOwnerId());
        if (ownerOpt.isEmpty()) throw new RuntimeException("Propietario no encontrado");

        EmergencyServices service = DestinationMapper.mapDtoToEntityForSave(
                emergencyServicesDTO, EmergencyServices.class, ownerOpt.get()
        );
        emergencyServicesRepository.save(service);

        if (files != null && !files.isEmpty()) {
            try {
                imageService.uploadImagesDestinations(files, service);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir imágenes", e);
            }
            emergencyServicesRepository.save(service);
        }

        return DestinationMapper.genericMapToTypedDTO(service, EmergencyServicesDTO.class);
    }

    @Override
    @Transactional
    public EmergencyServicesDTO updateEmergencyServices(Long idEmergency, List<MultipartFile> files, EmergencyServicesDTO emergencyServicesDTO) {
        Optional<EmergencyServices> serviceOpt = emergencyServicesRepository.findById(idEmergency);
        if (serviceOpt.isEmpty()) throw new RuntimeException("Servicio de emergencia no encontrado");

        Optional<User> ownerOpt = userRepository.findById(emergencyServicesDTO.getOwnerId());
        if (ownerOpt.isEmpty()) throw new RuntimeException("Propietario no encontrado");

        EmergencyServices service = serviceOpt.get();

        if (files != null && !files.isEmpty()) {
            for (var image : service.getImagesDestinations()) {
                try {
                    imageService.deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
                } catch (IOException e) {
                    throw new RuntimeException("Error al eliminar imágenes", e);
                }
            }
            try {
                imageService.uploadImagesDestinations(files, service);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir imágenes", e);
            }
        }

        DestinationMapper.mapDtoToEntityForUpdate(emergencyServicesDTO, service, ownerOpt.get());
        emergencyServicesRepository.save(service);

        return DestinationMapper.genericMapToTypedDTO(service, EmergencyServicesDTO.class);
    }

    @Override
    @Transactional
    public void deleteEmergencyServices(Long idEmergency) {
        Optional<EmergencyServices> serviceOpt = emergencyServicesRepository.findById(idEmergency);
        if (serviceOpt.isEmpty()) throw new RuntimeException("Servicio de emergencia no encontrado");

        EmergencyServices service = serviceOpt.get();

        for (var image : service.getImagesDestinations()) {
            try {
                imageService.deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
            } catch (IOException e) {
                throw new RuntimeException("Error al eliminar imágenes", e);
            }
        }

        emergencyServicesRepository.deleteById(idEmergency);
    }

    @Override
    public List<EmergencyServicesDTO> findAllByOrderByAverageScoreDesc() {
        List<EmergencyServices> services = emergencyServicesRepository.findAllByOrderByAverageScoreDesc();
        return DestinationMapper.genericMapListToTypedDTO(services, EmergencyServicesDTO.class);
    }

    @Override
    public List<EmergencyServicesDTO> dinamicFilterForEmergencyServices(String locality, Integer minAverageScore, TypeOfEmergency type) {
        Specification<EmergencyServices> spec = Specification.where(
                EmergencyServiceSpecification.hasLocality(locality)
        ).and(
                EmergencyServiceSpecification.hasAverageScoreGreaterOrEqual(minAverageScore)
        ).and(
                EmergencyServiceSpecification.hasType(type)
        );

        List<EmergencyServices> services = emergencyServicesRepository.findAll(spec);
        return DestinationMapper.genericMapListToTypedDTO(services, EmergencyServicesDTO.class);
    }
}
