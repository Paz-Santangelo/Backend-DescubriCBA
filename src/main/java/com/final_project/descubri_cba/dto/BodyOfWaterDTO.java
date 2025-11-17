package com.final_project.descubri_cba.dto;

import com.final_project.descubri_cba.enums.TypeBodyOfWater;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BodyOfWaterDTO extends DestinationDTO {

    @NotNull(message = "Debe especificar el tipo de cuerpo de agua.")
    private TypeBodyOfWater typeBodyOfWater;

    @Min(value = 0, message = "El precio de entrada no puede ser negativo.")
    private Double entrancePrice;

    @NotNull(message = "Debe indicar si el acceso es gratuito o no.")
    private Boolean freeAdmission;

    @NotBlank(message = "El nivel de limpieza es obligatorio.")
    private String cleaningLevel;

    // Validación condicional: si no es gratuito, el precio debe estar presente
    @AssertTrue(message = "Debe ingresar precio de entrada si no es acceso gratuito.")
    public boolean isEntrancePriceValid() {
        return Boolean.TRUE.equals(freeAdmission) || entrancePrice != null;
    }
    
}
