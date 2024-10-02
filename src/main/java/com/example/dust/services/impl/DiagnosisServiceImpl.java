package com.example.dust.services.impl;

import com.example.dust.domain.Appointment;
import com.example.dust.domain.Diagnosis;
import com.example.dust.dto.DiagnosisDTO;
import com.example.dust.repositories.DiagnosisRepository;
import com.example.dust.services.DiagnosisService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DiagnosisServiceImpl(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public DiagnosisDTO create(DiagnosisDTO diagnosisDTO) {
        Diagnosis diagnosis = modelMapper.map(diagnosisDTO, Diagnosis.class);
        diagnosisRepository.save(diagnosis);
        return diagnosisDTO;
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

    @Override
    public DiagnosisDTO update(DiagnosisDTO diagnosisDTO) {
        Diagnosis diagnosis = modelMapper.map(diagnosisDTO, Diagnosis.class);
        Diagnosis updatedDiagnosis = diagnosisRepository.update(diagnosis);
        return modelMapper.map(updatedDiagnosis, DiagnosisDTO.class);
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
