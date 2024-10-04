package com.example.dust.controller;


import com.example.dust.dto.TreatmentDTO;
import com.example.dust.services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treatment")
public class TreatmentController {

    private final TreatmentService treatmentService;

    @Autowired
    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @PostMapping("/create")
    public TreatmentDTO createTreatment(@RequestBody TreatmentDTO treatmentDTO) {
        return treatmentService.create(treatmentDTO);
    }

    @GetMapping("/{id}")
    public TreatmentDTO getTreatmentById(@PathVariable Integer id) {
        return treatmentService.getById(id);
    }

    @GetMapping("/all")
    public List<TreatmentDTO> getAllTreatments() {
        return treatmentService.getAll();
    }

    @PutMapping("/update")
    public TreatmentDTO updateTreatment(@RequestBody TreatmentDTO treatmentDTO) {
        return treatmentService.update(treatmentDTO);
    }

    @GetMapping("/treatments/{patientId}")
    public List<TreatmentDTO> getTreatmentsByPatientId(@PathVariable Integer patientId) {
        return treatmentService.findByPatientId(patientId);
    }
}
