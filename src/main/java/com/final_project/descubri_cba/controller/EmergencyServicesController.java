package com.final_project.descubri_cba.controller;

import com.final_project.descubri_cba.dto.EmergencyServicesDTO;
import com.final_project.descubri_cba.service.IEmergencyServicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/emergencyServices")
public class EmergencyServicesController {

    private final IEmergencyServicesService emergencyServicesService;

    public EmergencyServicesController(IEmergencyServicesService emergencyServicesService) {
        this.emergencyServicesService = emergencyServicesService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmergencyServicesDTO>> getAllServices() {
        return ResponseEntity.ok(emergencyServicesService.findAllEmergencyServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmergencyServicesDTO> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(emergencyServicesService.findEmergencyServiceById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<EmergencyServicesDTO> createService(
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @ModelAttribute EmergencyServicesDTO serviceDto) throws IOException {

        EmergencyServicesDTO created = emergencyServicesService.saveEmergencyServices(files, serviceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/update/{idEmergencyService}")
    public ResponseEntity<EmergencyServicesDTO> updateService(
            @PathVariable Long idEmergencyService,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @ModelAttribute EmergencyServicesDTO serviceDto) throws IOException {

        EmergencyServicesDTO updated = emergencyServicesService.updateEmergencyServices(idEmergencyService, files, serviceDto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("delete/{idEmergencyServices}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        emergencyServicesService.deleteEmergencyServices(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/allByOrderDescendent")
    public ResponseEntity<List<EmergencyServicesDTO>> getAllByScoreDesc() {
        return ResponseEntity.ok(emergencyServicesService.findAllByOrderByAverageScoreDesc());
    }

    @GetMapping("/dinamicFilter")
    public ResponseEntity<List<EmergencyServicesDTO>> getByDynamicFilter(
            @RequestParam(required = false) String locality,
            @RequestParam(required = false) Integer minAverageScore,
            @RequestParam(required = false) String type) {

        return ResponseEntity.ok(
                emergencyServicesService.dinamicFilterForEmergencyServices(locality, minAverageScore, type)
        );
    }
}
