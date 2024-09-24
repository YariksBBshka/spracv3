package com.example.dust.controller;

import com.example.dust.domain.Patient;
import com.example.dust.dto.PatientDTO;
import com.example.dust.services.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientServiceImpl patientService;

    @Autowired
    public PatientController(PatientServiceImpl patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
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

    @PutMapping("/update/{id}")
    public PatientDTO updatePatient(@PathVariable Integer id, @RequestBody PatientDTO patientDTO) {
        patientDTO.setId(id);
        return patientService.update(patientDTO);
    }

    @GetMapping("/search/{phoneNumber}")
    public PatientDTO findPatientByPhoneNumber(@PathVariable String phoneNumber) {
        return patientService.findByPhoneNumber(phoneNumber);
    }

}