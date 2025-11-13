package com.final_project.descubri_cba.controller;

import com.final_project.descubri_cba.dto.BodyOfWaterDTO;
import com.final_project.descubri_cba.service.IBodyOfWaterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bodyOfWaters")
public class BodyOfWaterController {

    private final IBodyOfWaterService bodyOfWaterService;

    public BodyOfWaterController(IBodyOfWaterService bodyOfWaterService) {
        this.bodyOfWaterService = bodyOfWaterService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BodyOfWaterDTO>> getAllBodies() {
        return ResponseEntity.ok(bodyOfWaterService.findAllBodiesOfWater());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BodyOfWaterDTO> getBodyById(@PathVariable Long id) {
        return ResponseEntity.ok(bodyOfWaterService.findBodyOfWaterById(id));
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGEMENT') or hasAuthority('OWNER')")
    @PostMapping("/create")
    public ResponseEntity<BodyOfWaterDTO> createBody(
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @Valid @ModelAttribute BodyOfWaterDTO bodyDto) throws IOException {

        BodyOfWaterDTO created = bodyOfWaterService.saveBodyOfWater(files, bodyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGEMENT') or hasAuthority('OWNER')")
    @PutMapping("/update/{idBodyOfWater}")
    public ResponseEntity<BodyOfWaterDTO> updateBody(
            @PathVariable("idBodyOfWater") Long idBodyOfWater,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @Valid @ModelAttribute BodyOfWaterDTO bodyDto) throws IOException {

        BodyOfWaterDTO updated = bodyOfWaterService.updateBodyOfWater(idBodyOfWater, files, bodyDto);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGEMENT') or hasAuthority('OWNER')")
    @DeleteMapping("/delete/{idBodyOfWater}")
    public ResponseEntity<String> deleteBody(@PathVariable("idBodyOfWater") Long idBodyOfWater) {
        bodyOfWaterService.deleteBodyOfWater(idBodyOfWater);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/allByOrderDescendent")
    public ResponseEntity<List<BodyOfWaterDTO>> getAllByScoreDesc() {
        return ResponseEntity.ok(bodyOfWaterService.findAllByOrderByAverageScoreDesc());
    }

    @GetMapping("/dinamicFilter")
    public ResponseEntity<List<BodyOfWaterDTO>> filterBodies(
            @RequestParam(required = false) String locality,
            @RequestParam(required = false) Integer minAverageScore,
            @RequestParam(required = false) Boolean freeAdmission,
            @RequestParam(required = false) String type) {

        return ResponseEntity.ok(
                bodyOfWaterService.dinamicFilterForBodyOfWater(locality, minAverageScore, freeAdmission, type));
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGEMENT') or hasAuthority('OWNER')")
    @GetMapping("/obtener/tipos")
    public ResponseEntity<List<String>> getBodyOfWaterTypes() {
        return ResponseEntity.ok(bodyOfWaterService.getBodyOfWaterTypes());
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGEMENT') or hasAuthority('OWNER')")
    @GetMapping("/obtener/niveles-limpieza")
    public ResponseEntity<List<String>> getCleaningLevels() {
        return ResponseEntity.ok(bodyOfWaterService.getCleaningLevels());
    }
}
