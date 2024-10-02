package com.example.dust.controller;


import com.example.dust.dto.AppointmentBookingDTO;
import com.example.dust.dto.AppointmentDTO;
import com.example.dust.services.impl.AppointmentServiceImpl;
import com.example.dust.services.impl.DoctorServiceImpl;
import com.example.dust.services.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentServiceImpl appointmentService;


    public AppointmentController(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/")
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.getAll();
    }

    @PostMapping("/booking")
    public AppointmentBookingDTO createAppointment(@RequestBody AppointmentBookingDTO appointmentBookingDTO) {
        return appointmentService.create(appointmentBookingDTO);
    }

    @PostMapping("/complete/{id}")
    public void completeAppointment(@PathVariable Integer id) {
        appointmentService.completeAppointment(appointmentService.getById(id));
    }

    @PostMapping("/cancel/{id}")
    public void cancelAppointment(@PathVariable Integer id) {
        appointmentService.cancelAppointment(appointmentService.getById(id));
    }

    @GetMapping("/appointmentHistory/{id}")
    public List<AppointmentBookingDTO> getHistory(@PathVariable Integer id) {
        return appointmentService.getAHistory(id);
    }

    @GetMapping("/available")
    public List<LocalTime> getAvailableSlots(@RequestParam Integer doctorId, @RequestParam LocalDate date) {
        return appointmentService.getAvailableTimeSlots(doctorId, date);
    }
}