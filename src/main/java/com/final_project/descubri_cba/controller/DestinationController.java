package com.final_project.descubri_cba.controller;

import com.final_project.descubri_cba.dto.DestinationDTO;
import com.final_project.descubri_cba.dto.DestinationCardDTO;
import com.final_project.descubri_cba.service.IDestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de destinos turísticos
 * Maneja endpoints para obtener información de destinos de Córdoba
 * Todos los endpoints requieren autenticación JWT excepto la consulta pública
 */
@RestController
@RequestMapping("/api/destinos")
public class DestinationController {

    @Autowired
    private IDestinationService destinationService;

    /**
     * Obtiene cards simplificadas de destinos turísticos para el frontend
     * Endpoint público optimizado para mostrar información resumida
     * @return Lista de cards con información básica de destinos
     */
    @GetMapping("/cards")
    public ResponseEntity<?> getAllDestinationCards() {
        List<DestinationCardDTO> cards = destinationService.getAllDestinationCards();
        return ResponseEntity.ok().body(cards);
    }

    /**
     * Obtiene destinos por tipo (restaurantes, alojamientos, cuerpos de agua, etc.)
     * @param tipo Tipo de destino a filtrar
     * @return Lista de destinos del tipo especificado
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> getDestinationsByType(@PathVariable String tipo) {
        List<DestinationDTO> destinations = destinationService.getDestinationsByType(tipo);
        return ResponseEntity.ok().body(destinations);
    }

    /**
     * Obtiene un destino específico por su ID
     * @param id ID del destino
     * @return Información detallada del destino
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getDestinationById(@PathVariable Long id) {
        DestinationDTO destination = destinationService.getDestinationById(id);
        return ResponseEntity.ok().body(destination);
    }

    /**
     * Busca destinos por su nombre.
     * @param query El nombre o parte del nombre del destino a buscar.
     * @return Lista de destinos que coinciden con la búsqueda.
     */
    @GetMapping("/search")
    public ResponseEntity<List<DestinationDTO>> searchDestinations(@RequestParam String query) {
        List<DestinationDTO> destinations = destinationService.searchDestinationsByName(query);
        return ResponseEntity.ok(destinations);
    }

    /**
     * Obtiene destinos por localidad de Córdoba, ignorando mayúsculas y minúsculas.
     * @param localidad Localidad de Córdoba
     * @return Lista de destinos en la localidad especificada
     */
    @GetMapping("/localidad/{localidad}")
    public ResponseEntity<List<DestinationDTO>> getDestinationsByLocality(@PathVariable String localidad) {
        List<DestinationDTO> destinations = destinationService.getDestinationsByLocality(localidad);
        return ResponseEntity.ok(destinations);
    }
}