package com.final_project.descubri_cba.specification;

import com.final_project.descubri_cba.enums.TypeBodyOfWater;
import com.final_project.descubri_cba.model.BodyOfWater;
import org.springframework.data.jpa.domain.Specification;

public class BodyOfWaterSpecification {

    public static Specification<BodyOfWater> hasLocality(String locality) {
        return (root, query, cb) -> locality == null ? null : cb.equal(cb.lower(root.get("locality")), locality.toLowerCase());
    }

    public static Specification<BodyOfWater> hasType(TypeBodyOfWater type) {
        return (root, query, cb) -> type == null ? null : cb.equal(root.get("typeBodyOfWater"), type);
    }

    public static Specification<BodyOfWater> hasFreeAdmission(Boolean freeAdmission) {
        return (root, query, cb) -> freeAdmission == null ? null : cb.equal(root.get("freeAdmission"), freeAdmission);
    }

    public static Specification<BodyOfWater> hasAverageScoreGreaterOrEqual(Integer minAverageScore) {
        return (root, query, cb) -> minAverageScore == null ? null : cb.greaterThanOrEqualTo(root.get("averageScore"), minAverageScore);
    }
}
