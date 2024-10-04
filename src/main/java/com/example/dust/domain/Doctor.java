package com.example.dust.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor extends BaseEntity {
    private String firstname;
    private String lastname;
    private String speciality;
    private Integer roomNumber;


    @Column(name = "firstname", nullable = false, length = 100)
    public String getFirstname() {
        return firstname;
    }

    @Column(name = "lastname", nullable = false, length = 100)
    public String getLastname() {
        return lastname;
    }

    @Column(name = "speciality", nullable = false, length = 100)
    public String getSpeciality() {
        return speciality;
    }

    @Column(name = "room_number", nullable = false)
    public Integer getRoomNumber() {
        return roomNumber;
    }

    public Doctor(String firstname, String lastname, String speciality, Integer roomNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.speciality = speciality;
        this.roomNumber = roomNumber;
    }

    protected Doctor(){
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }



}
