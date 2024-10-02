package com.example.dust.repositories;

import com.example.dust.domain.Treatment;

import java.util.List;
import java.util.Optional;

public interface TreatmentRepository {
    List<Treatment> findByPatientId(Integer patientId);
    Treatment save(Treatment treatment);
    List<Treatment> findAll();
    Treatment update(Treatment treatment);
    Optional<Treatment> findById(Integer id);
}
