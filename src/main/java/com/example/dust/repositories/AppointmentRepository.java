package com.example.dust.repositories;

import com.example.dust.domain.Appointment;
import com.example.dust.domain.Doctor;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    List<Appointment> findByPatientId(Integer id);
    List<Appointment> findByFkDoctorAndAppointmentDate(Doctor doctor, LocalDate date);
    Appointment save(Appointment appointment);
    List<Appointment> findAll();
    Appointment update(Appointment appointment);
    Optional<Appointment> findById(Integer id);
}
