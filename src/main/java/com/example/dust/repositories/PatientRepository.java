package com.example.dust.repositories;

import com.example.dust.domain.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    Patient findByPhoneNumber(String phoneNumber);
    Patient save(Patient patient);
    List<Patient> findAll();
    Patient update(Patient patient);
    Optional<Patient> findById(Integer id);
}
