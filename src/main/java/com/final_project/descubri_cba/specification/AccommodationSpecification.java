package com.final_project.descubri_cba.specification;

import com.final_project.descubri_cba.enums.AccommodationType;
import com.final_project.descubri_cba.model.Accommodation;
import org.springframework.data.jpa.domain.Specification;

public class AccommodationSpecification {

    public static Specification<Accommodation> hasLocality(String locality) {
        return (root, query, cb) -> locality == null ? null : cb.equal(cb.lower(root.get("locality")), locality.toLowerCase());
    }

    public static Specification<Accommodation> hasType(AccommodationType type) {
        return (root, query, cb) -> type == null ? null : cb.equal(root.get("type"), type);
    }

    public static Specification<Accommodation> hasAverageScoreGreaterOrEqual(Integer minAverageScore) {
        return (root, query, cb) -> minAverageScore == null ? null : cb.greaterThanOrEqualTo(root.get("averageScore"), minAverageScore);
    }
}
