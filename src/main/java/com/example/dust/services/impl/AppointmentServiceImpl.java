package com.example.dust.services.impl;
import com.example.dust.domain.Appointment;
import com.example.dust.domain.Doctor;
import com.example.dust.domain.Patient;
import com.example.dust.domain.enums.AppointmentStatus;
import com.example.dust.domain.enums.PatientStatus;
import com.example.dust.dto.AppointmentBookingDTO;
import com.example.dust.dto.AppointmentDTO;
import com.example.dust.repositories.AppointmentRepository;
import com.example.dust.repositories.DoctorRepository;
import com.example.dust.repositories.PatientRepository;
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

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private ModelMapper modelMapper;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public boolean isTimeAvailable(Doctor doctor, LocalDate date, LocalTime time, Patient patient) {
        List<Appointment> appointments = appointmentRepository.findByFkDoctorAndAppointmentDate(doctor, date)
                .stream()
                .filter(appointment -> !appointment.getStatus().equals(AppointmentStatus.CANCELLED) &&
                        !appointment.getStatus().equals(AppointmentStatus.COMPLETED))
                .collect(Collectors.toList());

        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentTime().equals(time) || isTimeSlotConflicting(appointment.getAppointmentTime(), time)) {
                return false;
            }
        }

        if (time.isAfter(LocalTime.of(12, 40)) && time.isBefore(LocalTime.of(14, 0))) {
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
        List<Appointment> appointments = appointmentRepository.findByFkDoctorAndAppointmentDate(doctor, date)
                .stream()
                .filter(appointment -> !appointment.getStatus().equals(AppointmentStatus.CANCELLED) &&
                        !appointment.getStatus().equals(AppointmentStatus.COMPLETED))
                .collect(Collectors.toList());

        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentTime().equals(time) || isTimeSlotConflicting(appointment.getAppointmentTime(), time)) {
                return false;
            }
        }

        if (time.isAfter(LocalTime.of(12, 40)) && time.isBefore(LocalTime.of(14, 0))) {
            return false;
        }

        return true;
    }


    @Override
    public boolean isTimeSlotConflicting(LocalTime timeSlot, LocalTime appointmentTime) {
        return timeSlot.isBefore(appointmentTime.plusMinutes(25)) && timeSlot.isAfter(appointmentTime.minusMinutes(25));
    }

    @Override
    public Appointment completeAppointment(Appointment appointment) {
        if (!(appointment.getStatus().equals(AppointmentStatus.CANCELLED))) {
            appointment.setStatus(AppointmentStatus.COMPLETED);
            return appointmentRepository.save(appointment);
        }
        else{
            return null;
        }
    }

    @Override
    public Appointment cancelAppointment(Appointment appointment) {
        if (!(appointment.getStatus().equals(AppointmentStatus.COMPLETED))) {
            appointment.setStatus(AppointmentStatus.CANCELLED);
            return appointmentRepository.save(appointment);
        }
        else{
            return null;
        }
    }

    @Override
    public List<AppointmentBookingDTO> getAHistory(Integer id) {
        return appointmentRepository.findByPatientId(id).stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentBookingDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalTime> getAvailableTimeSlots(Integer doctorId, LocalDate date) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        List<LocalTime> availableSlots = new ArrayList<>();

        for (LocalTime time = startTime; time.isBefore(endTime); time = time.plusMinutes(25)) {
            if (isTimeAvailable(doctor, date, time)) {
                availableSlots.add(time);
            }
        }

        return availableSlots;
    }



    @Override
    public AppointmentBookingDTO create(AppointmentBookingDTO appointmentBookingDTO) {
        Doctor doctor = doctorRepository.findById(appointmentBookingDTO.getDoctorId()).orElseThrow();
        Patient patient = patientRepository.findById(appointmentBookingDTO.getPatientId()).orElseThrow();
        LocalDate appointmentDate = appointmentBookingDTO.getLocalDate();
        LocalTime appointmentTime = appointmentBookingDTO.getLocalTime();

        List<Appointment> monthlyAppointments = appointmentRepository.findByPatientId(patient.getId()).stream()
                .filter(appointment -> appointment.getAppointmentDate().getMonth() == appointmentDate.getMonth() &&
                        appointment.getAppointmentDate().getYear() == appointmentDate.getYear())
                .collect(Collectors.toList());

        if (patient.getClientStatus().equals(PatientStatus.BASIC) && monthlyAppointments.size() >= 3) {
            throw new RuntimeException("Для клиентов BASIC разрешено не более 3 записей в месяц");
        }

        Appointment appointment = new Appointment(appointmentDate, appointmentTime, AppointmentStatus.BOOKED, doctor, patient);

        if (isTimeAvailable(doctor, appointmentDate, appointmentTime, patient)) {
            return modelMapper.map(appointmentRepository.save(appointment), AppointmentBookingDTO.class);
        } else {
            throw new RuntimeException("Time is not available");
        }
    }

    @Override
    public Appointment getById(Integer id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<AppointmentDTO> getAll() {
        return appointmentRepository.findAll().stream()
                .map(appointment -> {
                    AppointmentDTO appointmentDTO = modelMapper.map(appointment, AppointmentDTO.class);
                    appointmentDTO.setDoctorName(appointment.getFkDoctor().getFirstname() + " " + appointment.getFkDoctor().getLastname());
                    appointmentDTO.setPatientName(appointment.getFkPatient().getFirstname() + " " + appointment.getFkPatient().getLastname());
                    return appointmentDTO;
                })
                .collect(Collectors.toList());
    }
}
