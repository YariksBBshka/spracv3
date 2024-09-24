package com.example.dust.services.impl;

import com.example.dust.domain.Patient;
import com.example.dust.dto.PatientDTO;
import com.example.dust.repositories.impl.PatientRepositoryImpl;
import com.example.dust.services.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepositoryImpl patientRepositoryImpl;
    private final ModelMapper modelMapper;

    @Autowired
    public PatientServiceImpl(PatientRepositoryImpl patientRepositoryImpl) {
        this.patientRepositoryImpl = patientRepositoryImpl;
        this.modelMapper = new ModelMapper();
    }

    public PatientDTO create(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patientRepositoryImpl.save(patient);
        return patientDTO;
    }

    public PatientDTO getById(Integer id) {
        Patient patient = patientRepositoryImpl.findById(id).orElse(null);
        if (patient != null) {
            return modelMapper.map(patient, PatientDTO.class);
        }
        return null;
    }

    public List<PatientDTO> getAll() {
        return patientRepositoryImpl.findAll().stream()
                .map(patient -> modelMapper.map(patient, PatientDTO.class))
                .collect(Collectors.toList());
    }

    public PatientDTO update(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        Patient updatedPatient = patientRepositoryImpl.update(patient);
        return modelMapper.map(updatedPatient, PatientDTO.class);
    }

    public PatientDTO findByPhoneNumber(String phoneNumber) {
        Patient patient = patientRepositoryImpl.findByPhoneNumber(phoneNumber);
        return modelMapper.map(patient, PatientDTO.class);
    }
}
