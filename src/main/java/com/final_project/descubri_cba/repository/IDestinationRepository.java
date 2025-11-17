package com.final_project.descubri_cba.repository;

import com.final_project.descubri_cba.dto.DestinationCardDTO;
import com.final_project.descubri_cba.model.Destination;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDestinationRepository extends JpaRepository<Destination, Long> {
    /**
     * Obtiene una lista de localidades únicas, cada una con una imagen representativa.
     * Utiliza una consulta nativa de PostgreSQL con DISTINCT ON para un rendimiento óptimo.
     * La consulta selecciona el primer destino encontrado para cada localidad y busca su primera imagen.
     * @return Una lista de DestinationCardDTO con localidades sin repetir.
     */
    @Query(value = "SELECT d.id, d.locality, (SELECT i.image_url FROM images_destinations i WHERE i.destination_id = d.id LIMIT 1) AS imageUrl " +
                   "FROM (SELECT DISTINCT ON (locality) * FROM destinations WHERE locality IS NOT NULL AND locality <> '') d " +
                   "ORDER BY d.locality",
           nativeQuery = true)
    List<DestinationCardDTO> findDistinctLocalities();

    /**
     * Busca localidades que coincidan con un término de búsqueda, ignorando mayúsculas y minúsculas.
     * Devuelve una lista de localidades únicas, cada una con una imagen representativa.
     * @param searchTerm El término de búsqueda para las localidades.
     * @return Una lista de DestinationCardDTO con localidades filtradas sin repetir.
     */
    @Query(value = "SELECT d.id, d.locality, (SELECT i.image_url FROM images_destinations i WHERE i.destination_id = d.id LIMIT 1) AS imageUrl " +
            "FROM (SELECT DISTINCT ON (locality) * FROM destinations WHERE locality ILIKE CONCAT('%', :searchTerm, '%')) d " +
            "ORDER BY d.locality",
            nativeQuery = true)
    List<DestinationCardDTO> findDestinationsByLocalityLike(@Param("searchTerm") String searchTerm);

}
