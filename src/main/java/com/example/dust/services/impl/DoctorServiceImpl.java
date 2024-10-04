package com.example.dust.services.impl;

import com.example.dust.domain.Doctor;
import com.example.dust.dto.DoctorDTO;
import com.example.dust.repositories.DoctorRepository;
import com.example.dust.services.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public DoctorDTO create(DoctorDTO doctorDTO) {
        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);

        Doctor savedDoctor = doctorRepository.save(doctor);

        DoctorDTO responseDTO = modelMapper.map(savedDoctor, DoctorDTO.class);
        responseDTO.setId(savedDoctor.getId());
        return responseDTO;
    }

    @Override
    public DoctorDTO getById(Integer id) {
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if (doctor != null) {
            return modelMapper.map(doctor, DoctorDTO.class);
        }
        return null;
    }

    @Override
    public List<DoctorDTO> getAll() {
        return doctorRepository.findAll().stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public DoctorDTO update(DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(doctorDTO.getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        modelMapper.map(doctorDTO, doctor);

        Doctor updatedDoctor = doctorRepository.save(doctor);
        DoctorDTO responseDTO = modelMapper.map(updatedDoctor, DoctorDTO.class);
        return responseDTO;
    }

    @Override
    public List<DoctorDTO> findBySpeciality(String speciality) {
        List<Doctor> doctors = doctorRepository.findBySpeciality(speciality);
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDTO> findByFirstnameAndLastname(String firstname, String lastname) {
        List<Doctor> doctors = doctorRepository.findByFirstnameAndLastname(firstname, lastname);
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }
}
