package com.example.dust.controller;


import com.example.dust.dto.AppointmentBookingDTO;
import com.example.dust.dto.AppointmentDTO;
import com.example.dust.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/all")
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.getAll();
    }

    @PostMapping("/booking")
    public AppointmentBookingDTO createAppointment(@RequestBody AppointmentBookingDTO appointmentBookingDTO) {
        return appointmentService.create(appointmentBookingDTO);
    }

    @PostMapping("/bookingExclusion")
    public AppointmentBookingDTO createAppointmentExclusion(@RequestBody AppointmentBookingDTO appointmentBookingDTO) {
        return appointmentService.createExclusion(appointmentBookingDTO);
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

    @GetMapping("/byDate")
    public List<AppointmentDTO> getAppointmentsByDate(@RequestParam LocalDate date) {
        return appointmentService.findByDate(date);
    }
}