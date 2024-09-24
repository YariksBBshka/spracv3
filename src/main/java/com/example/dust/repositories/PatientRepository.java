package com.example.dust.repositories;

import com.example.dust.domain.Patient;
import org.springframework.stereotype.Repository;

public interface PatientRepository {
    Patient findByPhoneNumber(String phoneNumber);
}
