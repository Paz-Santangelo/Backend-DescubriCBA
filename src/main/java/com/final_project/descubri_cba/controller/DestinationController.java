package com.final_project.descubri_cba.controller;

import com.final_project.descubri_cba.dto.DestinationDTO;
import com.final_project.descubri_cba.dto.DestinationCardDTO;
import com.final_project.descubri_cba.service.IDestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinos")
public class DestinationController {

    @Autowired
    private IDestinationService destinationService;

    /* Obtiene cards simplificadas de destinos turísticos para el frontend */
    @GetMapping("/cards")
    public ResponseEntity<List<DestinationCardDTO>> getAllDestinationCards() {
        List<DestinationCardDTO> cards = destinationService.getAllDestinationCards();
        return ResponseEntity.ok().body(cards);
    }

    /* Obtiene los niveles de concurrencia para los destinos. */
    @GetMapping("/obtener/concurrencia")
    public ResponseEntity<List<String>> getConcurrenceLevels() {
        return ResponseEntity.ok(destinationService.getConcurrenceLevels());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<DestinationCardDTO>> findDestinationsByLocality(
            @RequestParam("localidad") String searchTerm) {
        List<DestinationCardDTO> cards = destinationService.findDestinationsByLocality(searchTerm);
        return ResponseEntity.ok().body(cards);
    }

    /* Obtiene un destino específico por su ID */
    @GetMapping("/{id}")
    public ResponseEntity<DestinationDTO> getDestinationById(@PathVariable Long id) {
        DestinationDTO destination = destinationService.getDestinationById(id);
        return ResponseEntity.ok().body(destination);
    }

    @GetMapping("/payment-methods")
    public ResponseEntity<List<String>> getPaymentMethods() {
        return ResponseEntity.ok(destinationService.getPaymentMethods());
    }
}