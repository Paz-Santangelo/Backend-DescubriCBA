package com.final_project.descubri_cba.utils;

import com.final_project.descubri_cba.dto.ImageDTO;
import com.final_project.descubri_cba.model.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImageMapper {
    public static <T extends ImageDTO> T convertEntityImageToImageDTO(Image image, Class<T> clazz) {
        try {
            T classDto = clazz.getDeclaredConstructor().newInstance();
            classDto.setId(image.getId());
            classDto.setUrlImage(image.getImageUrl());
            classDto.setName(image.getName());
            return classDto;
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir la entidad Imagen a DTO: " + e.getMessage());
        }
    }

    public static List<ImageDTO> convertEntityImageListToImageDTOList(List<? extends Image> images) {
        if (images == null) return new ArrayList<>();

        return images.stream().map(image -> {
            ImageDTO dto = new ImageDTO();
            dto.setId(image.getId());
            dto.setUrlImage(image.getImageUrl());
            dto.setName(image.getName());
            return dto;
        }).collect(Collectors.toList());
    }
}
