package com.final_project.descubri_cba.dto;

import com.final_project.descubri_cba.enums.AccommodationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDTO extends DestinationDTO {
    private AccommodationType type;
}
