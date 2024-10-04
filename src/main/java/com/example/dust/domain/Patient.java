package com.example.dust.domain;

import com.example.dust.domain.enums.PatientStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "patient")
public class Patient extends BaseEntity {
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private PatientStatus clientStatus;

    @Column(name = "firstname", nullable = false, length = 100)
    public String getFirstname() {
        return firstname;
    }

    @Column(name = "lastname", nullable = false, length = 100)
    public String getLastname() {
        return lastname;
    }

    @Column(name = "date_of_birth", nullable = false)
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Column(name = "address", nullable = false, length = 50)
    public String getAddress() {
        return address;
    }

    @Column(name = "phone_number", nullable = false, length = 50)
    public String getPhoneNumber() {
        return phoneNumber;
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "client_status", nullable = false, length = 50)
    public PatientStatus getClientStatus() {
        return clientStatus;
    }

    public Patient(String firstname, String lastname, LocalDate dateOfBirth, String address, String phoneNumber, PatientStatus clientStatus) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.clientStatus = clientStatus;
    }

    protected Patient(){

    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setClientStatus(PatientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }


}