package com.final_project.descubri_cba.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ICloudinaryService {
    @SuppressWarnings("rawtypes")
    Map upload(MultipartFile multipartFile) throws IOException;

    @SuppressWarnings("rawtypes")
    Map delete(String id_image) throws IOException;
}
