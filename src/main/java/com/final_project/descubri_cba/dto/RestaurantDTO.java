package com.final_project.descubri_cba.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private List<String> cuisineType;
    private boolean delivery;
    private boolean reservations;
}

