package com.example.dust.dto;


public class TreatmentDTO {
    private Integer id;
    private String medication;
    private String instructions;
    private DiagnosisDTO fkDiagnosis;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public DiagnosisDTO getFkDiagnosis() {
        return fkDiagnosis;
    }

    public void setFkDiagnosis(DiagnosisDTO fkDiagnosis) {
        this.fkDiagnosis = fkDiagnosis;
    }
}
