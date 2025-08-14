package com.final_project.descubri_cba.dto;

import com.final_project.descubri_cba.enums.TypeOfEmergency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyServicesDTO extends DestinationDTO {
    private TypeOfEmergency typeOfEmergency;
}
