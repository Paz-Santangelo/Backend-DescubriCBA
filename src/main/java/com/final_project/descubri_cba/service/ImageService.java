package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.model.Destination;
import com.final_project.descubri_cba.model.Image;
import com.final_project.descubri_cba.model.ImageDestination;
import com.final_project.descubri_cba.model.ImageUser;
import com.final_project.descubri_cba.repository.IImageDestinationRepository;
import com.final_project.descubri_cba.repository.IImageUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImageService implements IImageService {

    @Autowired
    private ICloudinaryService cloudinaryService;

    @Autowired
    private IImageUserRepository imageUserRepository;

    @Autowired
    private IImageDestinationRepository imageDestinationRepository;

    @Override
    public ImageUser uploadImageUser(MultipartFile file) throws IOException {
        Map<String, Object> uploadResult = cloudinaryService.upload(file);
        String imageUrl = (String) uploadResult.get("url");
        String imageId = (String) uploadResult.get("public_id");

        ImageUser imageUser = new ImageUser(file.getOriginalFilename(), imageUrl, imageId);

        return imageUserRepository.save(imageUser);
    }

    @Override
    public void deleteImageUser(ImageUser imageUser) throws IOException {
        deleteImageCloudinaryAndRepository(imageUser, imageUserRepository);
    }

    @Override
    public List<ImageDestination> uploadImagesDestinations(List<MultipartFile> files, Destination destination) throws IOException {
        return files.stream()
                .map(file -> {
                    try {

                        @SuppressWarnings("unchecked")
                        Map<String, Object> uploadResult = cloudinaryService.upload(file);
                        String imageUrl = (String) uploadResult.get("url");
                        String imageId = (String) uploadResult.get("public_id");

                        ImageDestination imageDestination = new ImageDestination(file.getOriginalFilename(), imageUrl, imageId, destination.getUser(), destination);
                        return imageDestinationRepository.save(imageDestination);

                    } catch (Exception e) {
                        throw new RuntimeException("Error al subir la imagen " + file.getOriginalFilename(), e);
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public void deleteImagesDestinations(Long destinationId) {
        List<ImageDestination> imagesDestinations = this.getImagesDestinationsByIdDestination(destinationId);

        imagesDestinations.forEach(image -> {
            try {
                deleteImageCloudinaryAndRepository(image, imageDestinationRepository);
            } catch (IOException e) {
                throw new RuntimeException("Error al eliminar la imagen con ID: " + image.getId(), e.getCause());
            }
        });
    }

    @Override
    public <T extends Image> void deleteImageCloudinaryAndRepository(T image, JpaRepository<T, Long> repository) throws IOException {
        cloudinaryService.delete(image.getImageId());
        repository.delete(image);
    }

    public List<ImageDestination> getImagesDestinationsByIdDestination(Long destinationId) {
        List<ImageDestination> imagesDestinations = imageDestinationRepository.findByDestination_Id(destinationId);

        if (imagesDestinations.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron imágenes para los destinos con ID: " + destinationId);
        }

        return imagesDestinations;
    }
}
