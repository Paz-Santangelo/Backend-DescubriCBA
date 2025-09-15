package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.AccommodationDTO;
import com.final_project.descubri_cba.enums.AccommodationType;
import com.final_project.descubri_cba.exception.CustomException;
import com.final_project.descubri_cba.model.Accommodation;
import com.final_project.descubri_cba.model.ImageDestination;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.repository.IAccommodationRepository;
import com.final_project.descubri_cba.repository.IImageDestinationRepository;
import com.final_project.descubri_cba.repository.IUserRepository;
import com.final_project.descubri_cba.specification.AccommodationSpecification;
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
public class AccommodationService implements IAccommodationService {
    @Autowired
    private IAccommodationRepository accommodationRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IImageService imageService;

    @Autowired
    private IImageDestinationRepository imageDestinationRepository;

    @Override
    public List<AccommodationDTO> findAllAccommodations() {
        List<Accommodation> accommodations = accommodationRepository.findAll();
        return DestinationMapper.genericMapListToTypedDTO(accommodations, AccommodationDTO.class);
    }

    @Override
    public AccommodationDTO findAccommodationById(Long idAccommodation) {
        Accommodation accommodationFound = accommodationRepository.findById(idAccommodation).orElseThrow(() -> new CustomException("Alojamiento no encontrado.", HttpStatus.NOT_FOUND));
        return (AccommodationDTO) DestinationMapper.mapToDestinationDTO(accommodationFound);
    }

    @Override
    @Transactional
    public AccommodationDTO saveAccommodation(List<MultipartFile> files, AccommodationDTO accommodationDTO) throws IOException {
            User ownerFound = userRepository.findById(accommodationDTO.getOwnerId()).orElseThrow(() -> new CustomException("Propietario no encontrado.", HttpStatus.NOT_FOUND));

            Accommodation accommodation = DestinationMapper.mapDtoToEntityForSave(accommodationDTO, Accommodation.class, ownerFound);

            Accommodation accommodationSaved = accommodationRepository.save(accommodation);

            if (files != null && !files.isEmpty()) {
                List<ImageDestination> images = imageService.uploadImagesDestinations(files, accommodationSaved);
                accommodationSaved.setImagesDestinations(images);
                accommodationSaved = accommodationRepository.save(accommodationSaved);
            }

            return (AccommodationDTO) DestinationMapper.mapToDestinationDTO(accommodationSaved);
    }

    @Override
    @Transactional
    public AccommodationDTO updateAccommodation(Long idAccommodation, List<MultipartFile> files, AccommodationDTO accommodationDTO) throws IOException {
        Accommodation accommodationFound = accommodationRepository.findById(idAccommodation).orElseThrow(() -> new CustomException("Alojamiento no encontrado.", HttpStatus.NOT_FOUND));

        User ownerFound = userRepository.findById(accommodationDTO.getOwnerId()).orElseThrow(() -> new CustomException("Propietario no encontrado.", HttpStatus.NOT_FOUND));

        if (files != null && !files.isEmpty()) {
            List<ImageDestination> existingImages = new ArrayList<>(accommodationFound.getImagesDestinations());
            for (ImageDestination image : existingImages) {
                accommodationFound.removeImageDestination(image);
                imageService.deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
            }

            List<ImageDestination> newImages = imageService.uploadImagesDestinations(files, accommodationFound);
            for (ImageDestination image : newImages) {
                accommodationFound.addImageDestination(image);
            }
        }

        DestinationMapper.mapDtoToEntityForUpdate(accommodationDTO, accommodationFound, ownerFound);

        Accommodation accommodationUpdated = accommodationRepository.save(accommodationFound);

        return (AccommodationDTO) DestinationMapper.mapToDestinationDTO(accommodationUpdated);
    }

    @Override
    public void deleteAccommodation(Long idAccommodation) {
        Accommodation accommodationFound = accommodationRepository.findById(idAccommodation).orElseThrow(() -> new CustomException("Alojamiento no encontrado.", HttpStatus.NOT_FOUND));
        accommodationRepository.delete(accommodationFound);
    }

    @Override
    public List<AccommodationDTO> findAllByOrderByAverageScoreDesc() {
        List<Accommodation> accommodations = accommodationRepository.findAllByOrderByAverageScoreDesc();
        return DestinationMapper.genericMapListToTypedDTO(accommodations, AccommodationDTO.class);
    }

    @Override
    public List<AccommodationDTO> dinamicFilterForAccommodation(String locality, Integer minAverageScore, String type) {
        AccommodationType typeAccommodation = type != null ? AccommodationType.fromString(type) : null;

        Specification<Accommodation> spec = Specification.where(AccommodationSpecification.hasLocality(locality))
                .and(AccommodationSpecification.hasAverageScoreGreaterOrEqual(minAverageScore))
                .and(AccommodationSpecification.hasType(typeAccommodation));

        List<Accommodation> accommodations = accommodationRepository.findAll(spec);
        return DestinationMapper.genericMapListToTypedDTO(accommodations, AccommodationDTO.class);
    }
}
