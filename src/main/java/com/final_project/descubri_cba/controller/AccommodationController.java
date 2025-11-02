package com.final_project.descubri_cba.controller;

import com.final_project.descubri_cba.dto.AccommodationDTO;
import com.final_project.descubri_cba.service.IAccommodationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/accommodations")
public class AccommodationController {

    private final IAccommodationService accommodationService;

    public AccommodationController(IAccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getAccommodationTypes() {
        return ResponseEntity.ok(accommodationService.getAccommodationTypes());
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccommodationDTO>> getAllAccommodations() {
        List<AccommodationDTO> accommodations = accommodationService.findAllAccommodations();
        return ResponseEntity.ok(accommodations);
    }

    @GetMapping("/{idAccommodation}")
    public ResponseEntity<AccommodationDTO> getAccommodationById(
            @PathVariable Long idAccommodation) {
        AccommodationDTO accommodation = accommodationService.findAccommodationById(idAccommodation);
        return ResponseEntity.ok(accommodation);
    }

    @PostMapping("/create")
    public ResponseEntity<AccommodationDTO> createAccommodation(
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @Valid @ModelAttribute AccommodationDTO accommodationDTO) throws IOException {
        AccommodationDTO created = accommodationService.saveAccommodation(files, accommodationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/update/{idAccommodation}")
    public ResponseEntity<AccommodationDTO> updateAccommodation(@PathVariable Long idAccommodation,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @Valid @ModelAttribute AccommodationDTO accommodationDTO) throws IOException {
        AccommodationDTO updated = accommodationService.updateAccommodation(idAccommodation, files, accommodationDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{idAccommodation}")
    public ResponseEntity<String> deleteAccommodation(@PathVariable Long idAccommodation) {
        accommodationService.deleteAccommodation(idAccommodation);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/allByOrderDescendent")
    public ResponseEntity<List<AccommodationDTO>> getAccommodationsByOrderDesc() {
        List<AccommodationDTO> accommodations = accommodationService.findAllByOrderByAverageScoreDesc();
        return ResponseEntity.ok(accommodations);
    }

    @GetMapping("/dinamicFilter")
    public ResponseEntity<List<AccommodationDTO>> filterAccommodations(
            @RequestParam(required = false) String locality,
            @RequestParam(required = false) Integer minAverageScore,
            @RequestParam(required = false) String type) {

        List<AccommodationDTO> accommodations = accommodationService
                .dinamicFilterForAccommodation(locality, minAverageScore, type);
        return ResponseEntity.ok(accommodations);
    }
}
