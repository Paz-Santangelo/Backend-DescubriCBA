package com.final_project.descubri_cba.dto;

import com.final_project.descubri_cba.enums.TypeOfEmergency;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyServicesDTO extends DestinationDTO {
    @NotNull(message = "Debe especificar el tipo de emergencia.")
    private TypeOfEmergency typeOfEmergency;
}
