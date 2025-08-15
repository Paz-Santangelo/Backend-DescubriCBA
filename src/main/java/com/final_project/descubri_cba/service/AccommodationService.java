package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.AccommodationDTO;
import com.final_project.descubri_cba.enums.AccommodationType;
import com.final_project.descubri_cba.model.Accommodation;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.repository.IAccommodationRepository;
import com.final_project.descubri_cba.repository.IImageDestinationRepository;
import com.final_project.descubri_cba.repository.IUserRepository;
import com.final_project.descubri_cba.specification.AccommodationSpecification;
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
public class AccommodationService implements IAccommodationService{
    @Autowired
    private IAccommodationRepository accommodationRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IImageService imageService;

    @Autowired
    private IImageDestinationRepository imageDestinationRepository;

    @Autowired
    private DestinationMapper destinationMapper;

    @Override
    public List<AccommodationDTO> findAllAccommodations() {
        List<Accommodation> accommodations = accommodationRepository.findAll();
        return DestinationMapper.genericMapListToTypedDTO(accommodations, AccommodationDTO.class);
    }

    @Override
    public AccommodationDTO findAccommodationById(Long idAccommodation) {
        Optional<Accommodation> accommodationOpt = accommodationRepository.findById(idAccommodation);
        if (accommodationOpt.isEmpty()) throw new RuntimeException("Alojamiento no encontrado");
        return DestinationMapper.genericMapToTypedDTO(accommodationOpt.get(), AccommodationDTO.class);
    }

    @Override
    @Transactional
    public AccommodationDTO saveAccommodation(List<MultipartFile> files, AccommodationDTO accommodationDTO) {
        Optional<User> ownerOpt = userRepository.findById(accommodationDTO.getOwnerId());
        if (ownerOpt.isEmpty()) throw new RuntimeException("Propietario no encontrado");

        Accommodation accommodation = DestinationMapper.mapDtoToEntityForSave(accommodationDTO, Accommodation.class, ownerOpt.get());
        accommodationRepository.save(accommodation);

        if (files != null && !files.isEmpty()) {
            try {
                imageService.uploadImagesDestinations(files, accommodation);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir imágenes", e);
            }
            accommodationRepository.save(accommodation);
        }

        return DestinationMapper.genericMapToTypedDTO(accommodation, AccommodationDTO.class);
    }

    @Override
    @Transactional
    public AccommodationDTO updateAccommodation(Long idAccommodation, List<MultipartFile> files, AccommodationDTO accommodationDTO) {
        Optional<Accommodation> accommodationOpt = accommodationRepository.findById(idAccommodation);
        if (accommodationOpt.isEmpty()) throw new RuntimeException("Alojamiento no encontrado");

        Optional<User> ownerOpt = userRepository.findById(accommodationDTO.getOwnerId());
        if (ownerOpt.isEmpty()) throw new RuntimeException("Propietario no encontrado");

        Accommodation accommodation = accommodationOpt.get();

        if (files != null && !files.isEmpty()) {
            for (var image : accommodation.getImagesDestinations()) {
                try {
                    imageService.deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
                } catch (IOException e) {
                    throw new RuntimeException("Error al eliminar imágenes", e);
                }
            }
            try {
                imageService.uploadImagesDestinations(files, accommodation);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir imágenes", e);
            }
        }

        DestinationMapper.mapDtoToEntityForUpdate(accommodationDTO, accommodation, ownerOpt.get());
        accommodationRepository.save(accommodation);

        return DestinationMapper.genericMapToTypedDTO(accommodation, AccommodationDTO.class);
    }

    @Override
    @Transactional
    public void deleteAccommodation(Long idAccommodation) {
        Optional<Accommodation> accommodationOpt = accommodationRepository.findById(idAccommodation);
        if (accommodationOpt.isEmpty()) throw new RuntimeException("Alojamiento no encontrado");

        Accommodation accommodation = accommodationOpt.get();

        for (var image : accommodation.getImagesDestinations()) {
            try {
                imageService.deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
            } catch (IOException e) {
                throw new RuntimeException("Error al eliminar imágenes", e);
            }
        }

        accommodationRepository.deleteById(idAccommodation);
    }

    @Override
    public List<AccommodationDTO> findAllByOrderByAverageScoreDesc() {
        List<Accommodation> accommodations = accommodationRepository.findAllByOrderByAverageScoreDesc();
        return DestinationMapper.genericMapListToTypedDTO(accommodations, AccommodationDTO.class);
    }

    @Override
    public List<AccommodationDTO> dinamicFilterForAccommodation(String locality, Integer minAverageScore, AccommodationType type) {
        Specification<Accommodation> spec = Specification.where(AccommodationSpecification.hasLocality(locality))
                .and(AccommodationSpecification.hasAverageScoreGreaterOrEqual(minAverageScore))
                .and(AccommodationSpecification.hasType(type));

        List<Accommodation> accommodations = accommodationRepository.findAll(spec);
        return DestinationMapper.genericMapListToTypedDTO(accommodations, AccommodationDTO.class);
    }
}
