package com.example.dust.services.impl;

import com.example.dust.domain.Patient;
import com.example.dust.domain.enums.PatientStatus;
import com.example.dust.dto.DiagnosisDTO;
import com.example.dust.dto.PatientDTO;
import com.example.dust.repositories.PatientRepository;
import com.example.dust.repositories.impl.PatientRepositoryImpl;
import com.example.dust.services.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PatientServiceImpl(PatientRepositoryImpl patientRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public PatientDTO create(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient.setClientStatus(PatientStatus.valueOf(patientDTO.getClientStatus().toUpperCase()));

        Patient savedPatient = patientRepository.save(patient);
        PatientDTO responseDTO = modelMapper.map(savedPatient, PatientDTO.class);
        responseDTO.setId(savedPatient.getId());
        return responseDTO;
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

    @Transactional
    @Override
    public PatientDTO update(PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(patientDTO.getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        modelMapper.map(patientDTO, patient);
        Patient updatedPatient = patientRepository.save(patient);
        PatientDTO responseDTO = modelMapper.map(updatedPatient, PatientDTO.class);
        return responseDTO;
    }


    @Override
    public PatientDTO findByPhoneNumber(String phoneNumber) {
        Patient patient = patientRepository.findByPhoneNumber(phoneNumber);
        return modelMapper.map(patient, PatientDTO.class);
    }
}
