package com.example.dust.services.impl;

import com.example.dust.domain.Appointment;
import com.example.dust.domain.Diagnosis;
import com.example.dust.domain.enums.AppointmentStatus;
import com.example.dust.dto.DiagnosisDTO;
import com.example.dust.repositories.AppointmentRepository;
import com.example.dust.repositories.DiagnosisRepository;
import com.example.dust.services.DiagnosisService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DiagnosisServiceImpl(DiagnosisRepository diagnosisRepository, AppointmentRepository appointmentRepository, ModelMapper modelMapper) {
        this.diagnosisRepository = diagnosisRepository;
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public DiagnosisDTO create(DiagnosisDTO diagnosisDTO) {
        Appointment appointment = appointmentRepository.findById(diagnosisDTO.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new RuntimeException("Cannot create a diagnosis unless the appointment is COMPLETED");
        }

        Diagnosis diagnosis = modelMapper.map(diagnosisDTO, Diagnosis.class);
        diagnosis.setFkAppointment(appointment);

        Diagnosis savedDiagnosis = diagnosisRepository.save(diagnosis);
        DiagnosisDTO responseDTO = modelMapper.map(savedDiagnosis, DiagnosisDTO.class);
        responseDTO.setId(savedDiagnosis.getId());
        return responseDTO;
    }


    @Override
    public DiagnosisDTO getById(Integer id) {
        Diagnosis diagnosis = diagnosisRepository.findById(id).orElse(null);
        if (diagnosis != null) {
            return modelMapper.map(diagnosis, DiagnosisDTO.class);
        }
        return null;
    }

    @Override
    public List<DiagnosisDTO> getAll() {
        return diagnosisRepository.findAll().stream()
                .map(diagnosis -> modelMapper.map(diagnosis, DiagnosisDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public DiagnosisDTO update(DiagnosisDTO diagnosisDTO) {
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisDTO.getId())
                .orElseThrow(() -> new RuntimeException("Diagnosis not found"));

        Appointment appointment = appointmentRepository.findById(diagnosisDTO.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        modelMapper.map(diagnosisDTO, diagnosis);
        diagnosis.setFkAppointment(appointment);

        Diagnosis updatedDiagnosis = diagnosisRepository.save(diagnosis);
        DiagnosisDTO responseDTO = modelMapper.map(updatedDiagnosis, DiagnosisDTO.class);
        return responseDTO;
    }

    @Override
    public List<DiagnosisDTO> findByPatientId(Integer patientId) {
        List<Diagnosis> diagnoses = diagnosisRepository.findByPatientId(patientId);
        return diagnoses.stream()
                .map(diagnosis -> modelMapper.map(diagnosis, DiagnosisDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DiagnosisDTO> findByFkAppointment(Appointment appointment) {
        List<Diagnosis> diagnoses = diagnosisRepository.findByFkAppointment(appointment);
        return diagnoses.stream()
                .map(diagnosis -> modelMapper.map(diagnosis, DiagnosisDTO.class))
                .collect(Collectors.toList());
    }
}
