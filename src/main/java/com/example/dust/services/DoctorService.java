package com.example.dust.services;


import com.example.dust.dto.DoctorDTO;

import java.util.List;

public interface DoctorService {
    DoctorDTO create(DoctorDTO doctorDTO);
    DoctorDTO getById(Integer id);
    List<DoctorDTO> getAll();
    DoctorDTO update(DoctorDTO doctorDTO);
    List<DoctorDTO> findByFirstnameAndLastname(String firstname, String lastname);
    List<DoctorDTO> findBySpeciality(String speciality);
}
