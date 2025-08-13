package com.final_project.descubri_cba.dto;

import com.final_project.descubri_cba.enums.TypeBodyOfWater;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BodyOfWaterDTO extends DestinationDTO {
    private TypeBodyOfWater typeBodyOfWater;
    private Double entrancePrice;
    private boolean freeAdmission;
    private String cleaningLevel;
}
