package com.example.dust.controller;


import com.example.dust.dto.TreatmentDTO;
import com.example.dust.services.impl.DiagnosisServiceImpl;
import com.example.dust.services.impl.TreatmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treatment")
public class TreatmentController {

    private final TreatmentServiceImpl treatmentService;
    private final DiagnosisServiceImpl diagnosisService;

    @Autowired
    public TreatmentController(TreatmentServiceImpl treatmentService, DiagnosisServiceImpl diagnosisServiceImpl) {
        this.treatmentService = treatmentService;
        this.diagnosisService = diagnosisServiceImpl;
    }

    @PostMapping("/create")
    public TreatmentDTO createTreatment(@RequestBody TreatmentDTO treatmentDTO) {
        return treatmentService.create(treatmentDTO);
    }

    @GetMapping("/{id}")
    public TreatmentDTO getTreatmentById(@PathVariable Integer id) {
        return treatmentService.getById(id);
    }

    @GetMapping("/")
    public List<TreatmentDTO> getAllTreatments() {
        return treatmentService.getAll();
    }

    @PutMapping("/update/{id}")
    public TreatmentDTO updateTreatment(@PathVariable Integer id, @RequestBody TreatmentDTO treatmentDTO) {
        TreatmentDTO existingTreatment = treatmentService.getById(id);
        if (existingTreatment != null) {
            return treatmentService.update(treatmentDTO);
        } else {
            throw new RuntimeException("Treatment not found");
        }
    }

    @GetMapping("/treatments/{patientId}")
    public List<TreatmentDTO> getTreatmentsByPatientId(@PathVariable Integer patientId) {
        return treatmentService.findByPatientId(patientId);
    }
}
