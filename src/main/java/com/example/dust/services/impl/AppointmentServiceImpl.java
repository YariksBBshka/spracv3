package com.example.dust.services.impl;
import com.example.dust.domain.Appointment;
import com.example.dust.domain.Doctor;
import com.example.dust.domain.Patient;
import com.example.dust.domain.enums.AppointmentStatus;
import com.example.dust.domain.enums.PatientStatus;
import com.example.dust.dto.AppointmentBookingDTO;
import com.example.dust.repositories.impl.AppointmentRepositoryImpl;
import com.example.dust.repositories.impl.DoctorRepositoryImpl;
import com.example.dust.repositories.impl.PatientRepositoryImpl;
import com.example.dust.services.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepositoryImpl appointmentRepositoryImpl;
    private final DoctorRepositoryImpl doctorRepositoryImpl;
    private final PatientRepositoryImpl patientRepositoryImpl;
    private ModelMapper modelMapper;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepositoryImpl appointmentRepositoryImpl, DoctorRepositoryImpl doctorRepositoryImpl, PatientRepositoryImpl patientRepositoryImpl) {
        this.appointmentRepositoryImpl = appointmentRepositoryImpl;
        this.doctorRepositoryImpl = doctorRepositoryImpl;
        this.patientRepositoryImpl = patientRepositoryImpl;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public boolean isTimeAvailable(Doctor doctor, LocalDate date, LocalTime time, Patient patient) {
        List<Appointment> appointments = appointmentRepositoryImpl.findByFkDoctorAndAppointmentDate(doctor, date);
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentTime().equals(time) || isTimeSlotConflicting(appointment.getAppointmentTime(), time)) {
                return false;
            }
        }
        if ((time.isAfter(LocalTime.of(12, 59)) && time.isBefore(LocalTime.of(14, 0)))) {
            return false;
        }
        if (patient.getClientStatus().equals(PatientStatus.VIP) && time.isBefore(LocalTime.of(19, 0)) && time.isAfter(LocalTime.of(10, 0))) {
            return true;
        } else if (patient.getClientStatus().equals(PatientStatus.BASIC) && time.isBefore(LocalTime.of(18, 0)) && time.isAfter(LocalTime.of(10, 0))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isTimeAvailable(Doctor doctor, LocalDate date, LocalTime time) {
        List<Appointment> appointments = appointmentRepositoryImpl.findByFkDoctorAndAppointmentDate(doctor, date);
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentTime().equals(time) || isTimeSlotConflicting(appointment.getAppointmentTime(), time)) {
                return false;
            }
        }
        if ((time.isAfter(LocalTime.of(12, 59)) && time.isBefore(LocalTime.of(14, 0)))) {
            return false;
        }
        return !time.isBefore(LocalTime.of(10, 0)) && !time.isAfter(LocalTime.of(18, 0));
    }



    @Override
    public boolean isTimeSlotConflicting(LocalTime timeSlot, LocalTime appointmentTime) {
        return timeSlot.isBefore(appointmentTime.plusMinutes(25)) && timeSlot.isAfter(appointmentTime.minusMinutes(25));
    }

    @Override
    public Appointment completeAppointment(Appointment appointment) {
        if (!(appointment.getStatus().equals(AppointmentStatus.CANCELLED))) {
            appointment.setStatus(AppointmentStatus.COMPLETED);
            return appointmentRepositoryImpl.save(appointment);
        }
        else{
            return null;
        }
    }

    @Override
    public Appointment cancelAppointment(Appointment appointment) {
        if (!(appointment.getStatus().equals(AppointmentStatus.COMPLETED))) {
            appointment.setStatus(AppointmentStatus.CANCELLED);
            return appointmentRepositoryImpl.save(appointment);
        }
        else{
            return null;
        }
    }

    @Override
    public List<AppointmentBookingDTO> getAHistory(Integer id) {
        return appointmentRepositoryImpl.findByPatientId(id).stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentBookingDTO.class))
                .collect(Collectors.toList());
    }

    public List<LocalTime> getAvailableTimeSlots(Integer doctorId, LocalDate date) {
        Doctor doctor = doctorRepositoryImpl.findById(doctorId).orElseThrow();
        List<Appointment> appointments = appointmentRepositoryImpl.findByFkDoctorAndAppointmentDate(doctor, date);
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        List<LocalTime> availableSlots = new ArrayList<>();

        for (LocalTime time = startTime; time.isBefore(endTime); time = time.plusMinutes(25)) {
            boolean isAvailable = true;
            for (Appointment appointment : appointments) {
                if (isTimeSlotConflicting(time, appointment.getAppointmentTime())) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable && isTimeAvailable(doctor, date, time) && !(time.isAfter(LocalTime.of(12, 59)) && time.isBefore(LocalTime.of(14, 0)))) {
                availableSlots.add(time);
            }
        }

        return availableSlots;
    }


    public AppointmentBookingDTO create(AppointmentBookingDTO appointmentBookingDTO) {
        Doctor doctor = doctorRepositoryImpl.findById(appointmentBookingDTO.getDoctorId()).orElseThrow();
        Patient patient = patientRepositoryImpl.findById(appointmentBookingDTO.getPatientId()).orElseThrow();
        LocalDate appointmentDate = appointmentBookingDTO.getLocalDate();
        LocalTime appointmentTime = appointmentBookingDTO.getLocalTime();

        List<Appointment> monthlyAppointments = appointmentRepositoryImpl.findByPatientId(patient.getId()).stream()
                .filter(appointment -> appointment.getAppointmentDate().getMonth() == appointmentDate.getMonth() &&
                        appointment.getAppointmentDate().getYear() == appointmentDate.getYear())
                .collect(Collectors.toList());

        if (patient.getClientStatus().equals(PatientStatus.BASIC) && monthlyAppointments.size() >= 3) {
            throw new RuntimeException("Для клиентов BASIC разрешено не более 3 записей в месяц");
        }

        Appointment appointment = new Appointment(appointmentDate, appointmentTime, AppointmentStatus.BOOKED, doctor, patient);

        if (isTimeAvailable(doctor, appointmentDate, appointmentTime, patient)) {
            return modelMapper.map(appointmentRepositoryImpl.save(appointment), AppointmentBookingDTO.class);
        } else {
            throw new RuntimeException("Time is not available");
        }
    }

    public Appointment getById(Integer id) {
        return appointmentRepositoryImpl.findById(id).orElse(null);
    }

    public List<AppointmentBookingDTO> getAll() {
        return appointmentRepositoryImpl.findAll().stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentBookingDTO.class))
                .collect(Collectors.toList());
    }

}
