package com.example.dust.repositories.impl;

import com.example.dust.domain.Doctor;
import com.example.dust.repositories.DoctorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class DoctorRepositoryImpl extends BaseRepository<Doctor, Integer> implements DoctorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public DoctorRepositoryImpl() {
        super(Doctor.class);
    }

    @Override
    public List<Doctor> findByFirstnameAndLastname(String firstname, String lastname) {
        return entityManager.createQuery("SELECT d FROM Doctor d WHERE d.firstname = :firstname AND d.lastname = :lastname", Doctor.class)
                .setParameter("firstname", firstname)
                .setParameter("lastname", lastname)
                .getResultList();
    }


    @Override
    public List<Doctor> findBySpeciality(String speciality) {
        return entityManager.createQuery("SELECT d FROM Doctor d WHERE d.speciality = :speciality", Doctor.class)
                .setParameter("speciality", speciality)
                .getResultList();
    }
}
