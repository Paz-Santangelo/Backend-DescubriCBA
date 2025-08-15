package com.final_project.descubri_cba.specification;

import com.final_project.descubri_cba.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

public class RestaurantSpecification {

    public static Specification<Restaurant> hasLocality(String locality) {
        return (root, query, cb) -> locality == null ? null : cb.equal(cb.lower(root.get("locality")), locality.toLowerCase());
    }

    public static Specification<Restaurant> hasAverageScoreGreaterOrEqual(Integer minAverageScore) {
        return (root, query, cb) -> minAverageScore == null ? null : cb.greaterThanOrEqualTo(root.get("averageScore"), minAverageScore);
    }

    public static Specification<Restaurant> hasDelivery(Boolean delivery) {
        return (root, query, cb) -> delivery == null ? null : cb.equal(root.get("delivery"), delivery);
    }

    public static Specification<Restaurant> hasReservations(Boolean reservations) {
        return (root, query, cb) -> reservations == null ? null : cb.equal(root.get("reservations"), reservations);
    }

    public static Specification<Restaurant> buildSpecification(String localidad, Integer minAverageScore, Boolean entrega, Boolean reservas) {
        Specification<Restaurant> spec = Specification.where(null);
        if (localidad != null && !localidad.isEmpty()) {
            spec = spec.and(hasLocality(localidad));
        }
        if (minAverageScore != null) {
            // Convertimos el int a double solo para la comparación del promedio
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("averageScore"), minAverageScore.doubleValue()));
        }
        if (entrega != null) {
            spec = spec.and(hasDelivery(entrega));
        }
        if (reservas != null) {
            spec = spec.and(hasReservations(reservas));
        }
        return spec;
    }

}
