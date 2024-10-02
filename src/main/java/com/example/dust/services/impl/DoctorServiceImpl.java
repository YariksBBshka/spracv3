package com.example.dust.services.impl;

import com.example.dust.domain.Doctor;
import com.example.dust.dto.DoctorDTO;
import com.example.dust.repositories.DoctorRepository;
import com.example.dust.services.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public DoctorDTO create(DoctorDTO doctorDTO) {
        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);
        doctorRepository.save(doctor);
        return doctorDTO;
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

    @Override
    public DoctorDTO update(DoctorDTO doctorDTO) {
        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);
        Doctor updatedDoctor = doctorRepository.update(doctor);
        return modelMapper.map(updatedDoctor, DoctorDTO.class);
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
