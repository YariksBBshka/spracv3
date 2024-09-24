package com.example.dust.repositories.impl;

import com.example.dust.domain.Patient;
import com.example.dust.repositories.PatientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class PatientRepositoryImpl extends BaseRepository<Patient, Integer> implements PatientRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PatientRepositoryImpl() {
        super(Patient.class);
    }

    @Override
    public Patient findByPhoneNumber(String phoneNumber) {
        return entityManager.createQuery("SELECT p FROM Patient p WHERE p.phoneNumber = :phoneNumber", Patient.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();
    }
}
