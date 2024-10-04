package com.example.dust.repositories.impl;

import com.example.dust.domain.Doctor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.dust.domain.Appointment;
import com.example.dust.repositories.AppointmentRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class AppointmentRepositoryImpl extends BaseRepository<Appointment, Integer> implements AppointmentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public AppointmentRepositoryImpl() {
        super(Appointment.class);
    }

    @Override
    public List<Appointment> findByPatientId(Integer id) {
        return entityManager.createQuery("SELECT a FROM Appointment a WHERE a.fkPatient.id = :patientid", Appointment.class)
                .setParameter("patientid", id)
                .getResultList();
    }
    @Override
    public List<Appointment> findByFkDoctorAndAppointmentDate(Doctor doctor, LocalDate date) {
        return entityManager.createQuery("SELECT a FROM Appointment a WHERE a.fkDoctor = :doctor AND a.appointmentDate = :date", Appointment.class)
                .setParameter("doctor", doctor)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    public List<Appointment> findByAppointmentDate(LocalDate date) {
        return entityManager.createQuery("SELECT a FROM Appointment a WHERE a.appointmentDate = :date", Appointment.class)
                .setParameter("date", date)
                .getResultList();
    }
}

