package com.final_project.descubri_cba.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.final_project.descubri_cba.model.ImageDestination;
import com.final_project.descubri_cba.model.Restaurant;
import com.final_project.descubri_cba.repository.IImageDestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ImageService {
    @Autowired
    private IImageDestinationRepository imageDestinationRepository;

    @Value("${cloudinary.url}")
    private String cloudinaryUrl;

    private Cloudinary cloudinary;

    @Autowired
    public void initCloudinary() {
        this.cloudinary = new Cloudinary(cloudinaryUrl);
    }

    public void uploadImagesDestinations(List<MultipartFile> files, Restaurant restaurant) {
        for (MultipartFile file : files) {
            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String url = (String) uploadResult.get("secure_url");
                String publicId = (String) uploadResult.get("public_id");
                ImageDestination image = new ImageDestination();
                image.setUrl(url);
                image.setPublicId(publicId);
                image.setDestination(restaurant);
                imageDestinationRepository.save(image);
                restaurant.getImagesDestinations().add(image);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir imagen a Cloudinary", e);
            }
        }
    }

    public void deleteImageCloudinaryAndRepository(List<ImageDestination> imagesDestinations) {
        for (ImageDestination image : imagesDestinations) {
            try {
                cloudinary.uploader().destroy(image.getPublicId(), ObjectUtils.emptyMap());
                imageDestinationRepository.delete(image);
            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar imagen de Cloudinary", e);
            }
        }
    }
}
