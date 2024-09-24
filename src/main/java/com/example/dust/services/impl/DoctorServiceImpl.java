package com.example.dust.services.impl;

import com.example.dust.domain.Doctor;
import com.example.dust.dto.DoctorDTO;
import com.example.dust.repositories.impl.DoctorRepositoryImpl;
import com.example.dust.services.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepositoryImpl doctorRepositoryImpl;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorServiceImpl(DoctorRepositoryImpl doctorRepositoryImpl) {
        this.doctorRepositoryImpl = doctorRepositoryImpl;
        this.modelMapper = new ModelMapper();
    }


    public DoctorDTO create(DoctorDTO doctorDTO) {
        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);
        doctorRepositoryImpl.save(doctor);
        return doctorDTO;
    }


    public DoctorDTO getById(Integer id) {
        Doctor doctor = doctorRepositoryImpl.findById(id).orElse(null);
        if (doctor != null) {
            return modelMapper.map(doctor, DoctorDTO.class);
        }
        return null;
    }


    public List<DoctorDTO> getAll() {
        return doctorRepositoryImpl.findAll().stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }


    public DoctorDTO update(DoctorDTO doctorDTO) {
        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);
        Doctor updatedDoctor = doctorRepositoryImpl.update(doctor);
        return modelMapper.map(updatedDoctor, DoctorDTO.class);
    }

    public List<DoctorDTO> findBySpeciality(String speciality) {
        List<Doctor> doctors = doctorRepositoryImpl.findBySpeciality(speciality);
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }

    public List<DoctorDTO> findByFirstnameAndLastname(String firstname, String lastname) {
        List<Doctor> doctors = doctorRepositoryImpl.findByFirstnameAndLastname(firstname, lastname);
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }
}
