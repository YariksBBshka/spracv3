package com.example.dust.repositories;

import com.example.dust.domain.Doctor;

import java.util.List;

public interface DoctorRepository {
    List<Doctor> findByFirstnameAndLastname(String firstname, String lastname);
    List<Doctor> findBySpeciality(String speciality);
}
