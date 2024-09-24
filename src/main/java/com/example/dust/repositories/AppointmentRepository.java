package com.example.dust.repositories;

import com.example.dust.domain.Appointment;
import com.example.dust.domain.Doctor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository {
    List<Appointment> findByPatientId(Integer id);
    List<Appointment> findByFkDoctorAndAppointmentDate(Doctor doctor, LocalDate date);
}
