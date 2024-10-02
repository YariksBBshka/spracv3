package com.example.dust.repositories;

import com.example.dust.domain.Appointment;
import com.example.dust.domain.Diagnosis;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiagnosisRepository {
    List<Diagnosis> findByPatientId(Integer patientId);
    List<Diagnosis> findByFkAppointment(Appointment appointment);
    Diagnosis save(Diagnosis diagnosis);
    List<Diagnosis> findAll();
    Diagnosis update(Diagnosis diagnosis);
    Optional<Diagnosis> findById(Integer id);
}
