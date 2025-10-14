package com.final_project.descubri_cba.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO extends DestinationDTO {
    @NotEmpty(message = "El tipo de cocina es obligatorio.")
    private List<String> cuisineType;
    @NotNull(message = "Debe especificar si tiene delivery.")
    private Boolean delivery;
    @NotNull(message = "Debe especificar si tiene reservas.")
    private Boolean reservations;
}

