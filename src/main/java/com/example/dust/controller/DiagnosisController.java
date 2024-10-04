package com.example.dust.controller;

import com.example.dust.domain.Appointment;
import com.example.dust.dto.DiagnosisDTO;
import com.example.dust.services.AppointmentService;
import com.example.dust.services.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diagnosis")
public class DiagnosisController {

    private final DiagnosisService diagnosisService;
    private final AppointmentService appointmentServiceImpl;

    @Autowired
    public DiagnosisController(DiagnosisService diagnosisService, AppointmentService appointmentServiceImpl) {
        this.diagnosisService = diagnosisService;
        this.appointmentServiceImpl = appointmentServiceImpl;
    }

    @PostMapping("/create")
    public DiagnosisDTO createDiagnosis(@RequestBody DiagnosisDTO diagnosisDTO) {
        return diagnosisService.create(diagnosisDTO);
    }

    @GetMapping("/{id}")
    public DiagnosisDTO getDiagnosisById(@PathVariable Integer id) {
        return diagnosisService.getById(id);
    }

    @GetMapping("/all")
    public List<DiagnosisDTO> getAllDiagnoses() {
        return diagnosisService.getAll();
    }

    @PutMapping("/update")
    public DiagnosisDTO updateDiagnosis(@RequestBody DiagnosisDTO diagnosisDTO) {
        return diagnosisService.update(diagnosisDTO);
    }

    @GetMapping("/patient/{patientId}")
    public List<DiagnosisDTO> getDiagnosisHistoryByPatientId(@PathVariable Integer patientId) {
        return diagnosisService.findByPatientId(patientId);
    }

    @GetMapping("/appointment/{appointmentId}")
    public List<DiagnosisDTO> getDiagnosesByAppointmentId(@PathVariable Integer appointmentId) {
        Appointment appointment = appointmentServiceImpl.getById(appointmentId);
        if (appointment != null) {
            return diagnosisService.findByFkAppointment(appointment);
        }
        return List.of();
    }
}
