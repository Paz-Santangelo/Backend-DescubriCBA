package com.final_project.descubri_cba.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
@Data

public class ImageDTO {

    private Long id;

    @NotNull(message = "La URL de la imagen no puede ser nula")
    @URL(message = "Debe ser una URL válida")
    private String urlImage;

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String name;

    public ImageDTO() {
    }

    public ImageDTO(Long id, String urlImage, String name) {
        this.id = id;
        this.urlImage = urlImage;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ImageDTO{" +
                "id=" + id +
                ", urlImage='" + urlImage + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}








