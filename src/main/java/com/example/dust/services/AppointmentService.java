package com.example.dust.services;

import com.example.dust.domain.Appointment;
import com.example.dust.domain.Doctor;
import com.example.dust.domain.Patient;
import com.example.dust.dto.AppointmentBookingDTO;
import com.example.dust.dto.AppointmentDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface AppointmentService{
    boolean isTimeAvailable(Doctor doctor, LocalDate date, LocalTime time, Patient patient);

    boolean isTimeAvailable(Doctor doctor, LocalDate date, LocalTime time);

    boolean isTimeSlotConflicting(LocalTime timeSlot, LocalTime appointmentTime);
    Appointment completeAppointment(Appointment appointment);
    Appointment cancelAppointment(Appointment appointment);
    List<AppointmentBookingDTO> getAHistory(Integer id);
    List<LocalTime> getAvailableTimeSlots(Integer doctorId, LocalDate date);
    AppointmentBookingDTO create(AppointmentBookingDTO appointmentBookingDTO);
    AppointmentBookingDTO createExclusion(AppointmentBookingDTO appointmentBookingDTO);
    Appointment getById(Integer id);
    List<AppointmentDTO> getAll();
    List<AppointmentDTO> findByDate(LocalDate date);

}
