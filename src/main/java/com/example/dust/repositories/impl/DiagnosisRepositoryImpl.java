package com.example.dust.repositories.impl;

import com.example.dust.domain.Appointment;
import com.example.dust.domain.Diagnosis;
import com.example.dust.repositories.DiagnosisRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiagnosisRepositoryImpl extends BaseRepository<Diagnosis, Integer> implements DiagnosisRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public DiagnosisRepositoryImpl() {
        super(Diagnosis.class);
    }

    @Override
    public List<Diagnosis> findByPatientId(Integer patientId) {
        return entityManager.createQuery("SELECT d FROM Diagnosis d "
                        + "JOIN d.fkAppointment a "
                        + "JOIN a.fkPatient p "
                        + "WHERE p.id = :patientId", Diagnosis.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }

    @Override
    public List<Diagnosis> findByFkAppointment(Appointment appointment) {
        return entityManager.createQuery("SELECT d FROM Diagnosis d WHERE d.fkAppointment = :appointment", Diagnosis.class)
                .setParameter("appointment", appointment)
                .getResultList();
    }
}
