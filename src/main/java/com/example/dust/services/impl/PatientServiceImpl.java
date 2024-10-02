package com.example.dust.services.impl;

import com.example.dust.domain.Patient;
import com.example.dust.dto.PatientDTO;
import com.example.dust.repositories.PatientRepository;
import com.example.dust.repositories.impl.PatientRepositoryImpl;
import com.example.dust.services.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PatientServiceImpl(PatientRepositoryImpl patientRepository) {
        this.patientRepository = patientRepository;
        this.modelMapper = new ModelMapper();
    }

    public PatientDTO create(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patientRepository.save(patient);
        return patientDTO;
    }

    @Override
    public PatientDTO getById(Integer id) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient != null) {
            return modelMapper.map(patient, PatientDTO.class);
        }
        return null;
    }

    @Override
    public List<PatientDTO> getAll() {
        return patientRepository.findAll().stream()
                .map(patient -> modelMapper.map(patient, PatientDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO update(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        Patient updatedPatient = patientRepository.update(patient);
        return modelMapper.map(updatedPatient, PatientDTO.class);
    }

    @Override
    public PatientDTO findByPhoneNumber(String phoneNumber) {
        Patient patient = patientRepository.findByPhoneNumber(phoneNumber);
        return modelMapper.map(patient, PatientDTO.class);
    }
}
