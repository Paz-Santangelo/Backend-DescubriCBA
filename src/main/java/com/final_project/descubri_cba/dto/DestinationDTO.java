package com.final_project.descubri_cba.dto;


import com.final_project.descubri_cba.enums.Concurrence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class DestinationDTO {
    private Long id;
    @NotBlank(message = "El nombre es obligatorio.")
    private String name;
    @NotBlank(message = "El departamento es obligatorio.")
    private String department;
    @NotBlank(message = "La localidad es obligatoria.")
    private String locality;
    @NotBlank(message = "La dirección es obligatoria.")
    private String address;
    private String urlGoogleMaps;
    @NotBlank(message = "El horario de apertura es obligatorio.")
    private String openingTime;
    @NotBlank(message = "El horario de cierre es obligatorio.")
    private String closingTime;
    @NotNull(message = "Debe aclarar el nivel de concurrencia.")
    private Concurrence levelConcurrence;
    @NotNull(message = "Debe aclarar si el lugar es accesible para personas con discapacidad.")
    private Boolean disabledAccessibility;
    private String numberPhone;
    @NotBlank(message = "El número de celular es obligatorio.")
    private String cellPhone;
    private List<String> website;
    @NotEmpty(message = "Debe aclarar los medios de pago.")
    private List<String> paymentMethods;
    private List<ImageDTO> imagesDestinations = new ArrayList<>();
    private List<RatingDTO> ratings;
    private List<CommentDTO> comments;
    private int averageScore;
    private Long ownerId;
}
