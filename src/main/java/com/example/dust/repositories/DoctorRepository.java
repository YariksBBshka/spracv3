package com.example.dust.repositories;

import com.example.dust.domain.Doctor;


import java.util.List;
import java.util.Optional;

public interface DoctorRepository {
    List<Doctor> findByFirstnameAndLastname(String firstname, String lastname);
    List<Doctor> findBySpeciality(String speciality);
    Doctor save(Doctor doctor);
    List<Doctor> findAll();
    Doctor update(Doctor doctor);
    Optional<Doctor> findById(Integer id);
}
