package com.example.dust.services;

import com.example.dust.domain.Appointment;
import com.example.dust.domain.Diagnosis;
import com.example.dust.dto.DiagnosisDTO;

import java.util.List;
import java.util.UUID;

public interface DiagnosisService {
    public List<DiagnosisDTO> findByPatientId(Integer patientId);
    public List<DiagnosisDTO> findByFkAppointment(Appointment appointment);
}
