package com.example.dust.controller;

import com.example.dust.dto.PatientDTO;
import com.example.dust.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/all")
    public List<PatientDTO> getAllPatients() {
        return patientService.getAll();
    }

    @GetMapping("/{id}")
    public PatientDTO getPatientById(@PathVariable Integer id) {
        return patientService.getById(id);
    }

    @PostMapping("/create")
    public PatientDTO createPatient(@RequestBody PatientDTO patientDTO) {
        return patientService.create(patientDTO);
    }

    @PutMapping("/update")
    public PatientDTO updatePatient(@RequestBody PatientDTO patientDTO) {
        return patientService.update(patientDTO);
    }

    @GetMapping("/search/{phoneNumber}")
    public PatientDTO findPatientByPhoneNumber(@PathVariable String phoneNumber) {
        return patientService.findByPhoneNumber(phoneNumber);
    }

}