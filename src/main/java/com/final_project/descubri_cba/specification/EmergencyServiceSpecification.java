package com.final_project.descubri_cba.specification;

import com.final_project.descubri_cba.enums.TypeBodyOfWater;
import com.final_project.descubri_cba.enums.TypeOfEmergency;
import com.final_project.descubri_cba.model.BodyOfWater;
import com.final_project.descubri_cba.model.EmergencyServices;
import org.springframework.data.jpa.domain.Specification;

public class EmergencyServiceSpecification {

    public static Specification<EmergencyServices> hasLocality(String locality) {
        return (root, query, cb) -> locality == null ? null : cb.equal(cb.lower(root.get("locality")), locality.toLowerCase());
    }

    public static Specification<EmergencyServices> hasType(TypeOfEmergency type) {
        return (root, query, cb) -> type == null ? null : cb.equal(root.get("typeOfEmergency"), type);
    }

    public static Specification<EmergencyServices> hasAverageScoreGreaterOrEqual(Integer minAverageScore) {
        return (root, query, cb) -> minAverageScore == null ? null : cb.greaterThanOrEqualTo(root.get("averageScore"), minAverageScore);
    }
}
