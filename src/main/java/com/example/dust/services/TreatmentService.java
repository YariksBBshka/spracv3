package com.example.dust.services;

import com.example.dust.domain.Diagnosis;
import com.example.dust.dto.TreatmentDTO;

import java.util.List;

public interface TreatmentService {
    public List<TreatmentDTO> findByPatientId(Integer patientId);
}
