package com.final_project.descubri_cba.converter;

import com.final_project.descubri_cba.enums.AccommodationType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAccommodationTypeConverter implements Converter<String, AccommodationType> {

    @Override
    public AccommodationType convert(String source) {
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        return AccommodationType.fromString(source);
    }
}
