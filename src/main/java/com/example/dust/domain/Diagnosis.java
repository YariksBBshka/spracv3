package com.example.dust.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "diagnosis")
public class Diagnosis extends BaseEntity {
    private String disease;
    private String description;
    private Appointment fkAppointment;

    @Column(name = "disease", nullable = false)
    public String getDisease() {
        return disease;
    }

    @Column(name = "description", nullable = false, length = 4095)
    public String getDescription() {
        return description;
    }
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fk_appointment_id", nullable = false)
    public Appointment getFkAppointment() {
        return fkAppointment;
    }

    public Diagnosis(String disease, String description, Appointment fkAppointment) {
        this.disease = disease;
        this.description = description;
        this.fkAppointment = fkAppointment;
    }

    protected Diagnosis() {
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFkAppointment(Appointment fkAppointment) {
        this.fkAppointment = fkAppointment;
    }

}