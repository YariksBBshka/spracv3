package com.example.dust.domain;

import com.example.dust.domain.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "appointment")
public class Appointment extends BaseEntity {
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus status;
    private Doctor fkDoctor;
    private Patient fkPatient;
    private List<Diagnosis> diagnoses;

    @Column(name = "appointment_date", nullable = false)
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }
    @Column(name = "appointment_time", nullable = false)
    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_doctor_id", nullable = false)
    public Doctor getFkDoctor() {
        return fkDoctor;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_patient_id", nullable = false)
    public Patient getFkPatient() {
        return fkPatient;
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 100)
    public AppointmentStatus getStatus() {
        return status;
    }

    public Appointment(LocalDate appointmentDate, LocalTime appointmentTime, AppointmentStatus status, Doctor fkDoctor, Patient fkPatient) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.fkDoctor = fkDoctor;
        this.fkPatient = fkPatient;
    }

    protected Appointment(){
    }

    public Appointment setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
        return this;
    }

    public Appointment setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
        return this;
    }

    public Appointment setFkDoctor(Doctor fkDoctor) {
        this.fkDoctor = fkDoctor;
        return this;
    }

    public Appointment setFkPatient(Patient fkPatient) {
        this.fkPatient = fkPatient;
        return this;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
@   JsonIgnore
    @OneToMany(mappedBy = "fkAppointment", fetch = FetchType.EAGER)
    public List<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(List<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }


}
