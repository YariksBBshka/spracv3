package com.example.dust.services;

import com.example.dust.dto.PatientDTO;

import java.util.List;

public interface PatientService {
    PatientDTO create(PatientDTO patientDTO);
    PatientDTO getById(Integer id);
    List<PatientDTO> getAll();
    PatientDTO update(PatientDTO patientDTO);
    PatientDTO findByPhoneNumber(String phoneNumber);
}
