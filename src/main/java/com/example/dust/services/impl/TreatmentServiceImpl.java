package com.example.dust.services.impl;


import com.example.dust.domain.Treatment;
import com.example.dust.dto.TreatmentDTO;
import com.example.dust.repositories.TreatmentRepository;

import com.example.dust.services.TreatmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TreatmentServiceImpl(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public TreatmentDTO create(TreatmentDTO treatmentDTO) {
        Treatment treatment = modelMapper.map(treatmentDTO, Treatment.class);
        treatmentRepository.save(treatment);
        return treatmentDTO;
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

    @Override
    public TreatmentDTO update(TreatmentDTO treatmentDTO) {
        Treatment treatment = modelMapper.map(treatmentDTO, Treatment.class);
        Treatment updatedTreatment = treatmentRepository.update(treatment);
        return modelMapper.map(updatedTreatment, TreatmentDTO.class);
    }

    @Override
    public List<TreatmentDTO> findByPatientId(Integer patientId) {
        List<Treatment> treatments = treatmentRepository.findByPatientId(patientId);
        return treatments.stream()
                .map(treatment -> modelMapper.map(treatment, TreatmentDTO.class))
                .collect(Collectors.toList());
    }
}
