package com.example.dust.services;

import com.example.dust.domain.Appointment;
import com.example.dust.domain.Diagnosis;
import com.example.dust.domain.Diagnosis;
import com.example.dust.dto.DiagnosisDTO;

import java.util.List;


public interface DiagnosisService {
    List<DiagnosisDTO> findByPatientId(Integer patientId);
    DiagnosisDTO create(DiagnosisDTO diagnosisDTO);
    DiagnosisDTO getById(Integer id);
    List<DiagnosisDTO> getAll();
    List<DiagnosisDTO> findByFkAppointment(Appointment appointment);
    DiagnosisDTO update(DiagnosisDTO diagnosisDTO);
}
