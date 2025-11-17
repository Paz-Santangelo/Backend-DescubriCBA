package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.model.Destination;
import com.final_project.descubri_cba.model.Image;
import com.final_project.descubri_cba.model.ImageDestination;
import com.final_project.descubri_cba.model.ImageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImageService {
    public ImageUser uploadImageUser(MultipartFile file) throws IOException;

    public void deleteImageUser(ImageUser imageUser) throws IOException;

    public List<ImageDestination> uploadImagesDestinations(List<MultipartFile> files, Destination destination) throws IOException;

    public void deleteImagesDestinations(Long destinationId);

    public <T extends Image> void deleteImageCloudinaryAndRepository(T image, JpaRepository<T, Long> repository) throws IOException;
}
