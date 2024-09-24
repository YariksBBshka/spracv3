package com.example.dust.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "treatment")
public class Treatment extends BaseEntity {
    private String medication;
    private String instructions;
    private Diagnosis fkDiagnosis;

    @Column(name = "medication", nullable = false, length = 4095)
    public String getMedication() {
        return medication;
    }

    @Column(name = "instructions", nullable = false, length = 4095)
    public String getInstructions() {
        return instructions;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fk_diagnosis_id", nullable = false)
    public Diagnosis getFkDiagnosis() {
        return fkDiagnosis;
    }

    public Treatment(String medication, String instructions, Diagnosis fkDiagnosis) {
        this.medication = medication;
        this.instructions = instructions;
        this.fkDiagnosis = fkDiagnosis;
    }

    protected Treatment() {
    }
    public void setMedication(String medication) {
        this.medication = medication;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public void setFkDiagnosis(Diagnosis fkDiagnosis) {
        this.fkDiagnosis = fkDiagnosis;
    }

}