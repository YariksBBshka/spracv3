package com.example.dust.repositories;

import com.example.dust.domain.Appointment;
import com.example.dust.domain.Diagnosis;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface DiagnosisRepository {
    List<Diagnosis> findByPatientId(Integer patientId);
    List<Diagnosis> findByFkAppointment(Appointment appointment);

}
