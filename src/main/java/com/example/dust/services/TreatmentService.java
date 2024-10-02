package com.example.dust.services;


import com.example.dust.dto.TreatmentDTO;


import java.util.List;

public interface TreatmentService {
    List<TreatmentDTO> findByPatientId(Integer patientId);
    TreatmentDTO create(TreatmentDTO treatmentDTO);
    TreatmentDTO getById(Integer id);
    List<TreatmentDTO> getAll();
    TreatmentDTO update(TreatmentDTO treatmentDTO);
}
