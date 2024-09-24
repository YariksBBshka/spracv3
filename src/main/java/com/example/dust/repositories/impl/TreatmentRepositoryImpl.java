package com.example.dust.repositories.impl;

import com.example.dust.domain.Diagnosis;
import com.example.dust.domain.Treatment;
import com.example.dust.repositories.TreatmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TreatmentRepositoryImpl extends BaseRepository<Treatment, Integer> implements TreatmentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public TreatmentRepositoryImpl() {
        super(Treatment.class);
    }

    @Override
    public List<Treatment> findByPatientId(Integer patientId) {
        return entityManager.createQuery("SELECT t FROM Treatment t "
                        + "JOIN t.fkDiagnosis d "
                        + "JOIN d.fkAppointment a "
                        + "JOIN a.fkPatient p "
                        + "WHERE p.id = :patientId", Treatment.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }
}
