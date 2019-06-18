package com.example.rnv_pfg.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Diagnosis {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("treatment")
    @Expose
    private String treatment;
    @SerializedName("appointment")
    @Expose
    private Integer appointment;

    public Diagnosis(String description, String treatment, Integer appointment) {
        this.description = description;
        this.treatment = treatment;
        this.appointment = appointment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public Integer getAppointment() {
        return appointment;
    }

    public void setAppointment(Integer appointment) {
        this.appointment = appointment;
    }

}
