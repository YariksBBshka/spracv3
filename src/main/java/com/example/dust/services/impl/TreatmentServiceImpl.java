package com.example.dust.services.impl;


import com.example.dust.domain.Diagnosis;
import com.example.dust.domain.Treatment;
import com.example.dust.dto.TreatmentDTO;
import com.example.dust.repositories.DiagnosisRepository;
import com.example.dust.repositories.TreatmentRepository;

import com.example.dust.services.TreatmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private final DiagnosisRepository diagnosisRepository;
    private final TreatmentRepository treatmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TreatmentServiceImpl(DiagnosisRepository diagnosisRepository, TreatmentRepository treatmentRepository, ModelMapper modelMapper) {
        this.diagnosisRepository = diagnosisRepository;
        this.treatmentRepository = treatmentRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public TreatmentDTO create(TreatmentDTO treatmentDTO) {
        Diagnosis diagnosis = diagnosisRepository.findById(treatmentDTO.getDiagnosisId())
                .orElseThrow(() -> new RuntimeException("Diagnosis not found"));

        Treatment treatment = modelMapper.map(treatmentDTO, Treatment.class);
        treatment.setFkDiagnosis(diagnosis);
        Treatment savedTreatment = treatmentRepository.save(treatment);

        TreatmentDTO responseDTO = modelMapper.map(savedTreatment, TreatmentDTO.class);
        responseDTO.setId(savedTreatment.getId());
        return responseDTO;
    }


    @Override
    public TreatmentDTO getById(Integer id) {
        Treatment treatment = treatmentRepository.findById(id).orElse(null);
        if (treatment != null) {
            return modelMapper.map(treatment, TreatmentDTO.class);
        }
        return null;
    }

    @Override
    public List<TreatmentDTO> getAll() {
        return treatmentRepository.findAll().stream()
                .map(treatment -> modelMapper.map(treatment, TreatmentDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TreatmentDTO update(TreatmentDTO treatmentDTO) {
        Treatment treatment = treatmentRepository.findById(treatmentDTO.getId())
                .orElseThrow(() -> new RuntimeException("Treatment not found"));

        if (treatmentDTO.getDiagnosisId() == null) {
            throw new IllegalArgumentException("Diagnosis ID must not be null for update");
        }

        Diagnosis diagnosis = diagnosisRepository.findById(treatmentDTO.getDiagnosisId())
                .orElseThrow(() -> new RuntimeException("Diagnosis not found"));

        modelMapper.map(treatmentDTO, treatment);
        treatment.setFkDiagnosis(diagnosis);
        Treatment updatedTreatment = treatmentRepository.save(treatment);
        TreatmentDTO responseDTO = modelMapper.map(updatedTreatment, TreatmentDTO.class);
        return responseDTO;
    }



    @Override
    public List<TreatmentDTO> findByPatientId(Integer patientId) {
        List<Treatment> treatments = treatmentRepository.findByPatientId(patientId);
        return treatments.stream()
                .map(treatment -> modelMapper.map(treatment, TreatmentDTO.class))
                .collect(Collectors.toList());
    }
}
