package com.example.dust.repositories;

import com.example.dust.domain.Diagnosis;
import com.example.dust.domain.Treatment;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TreatmentRepository {
    List<Treatment> findByPatientId(Integer patientId);
}
