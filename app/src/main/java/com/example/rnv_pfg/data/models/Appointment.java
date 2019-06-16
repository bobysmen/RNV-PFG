package com.example.rnv_pfg.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Appointment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_employee")
    @Expose
    private Integer idEmployee;
    @SerializedName("id_patient")
    @Expose
    private Integer idPatient;
    @SerializedName("date")
    @Expose
    private String date;

    public Appointment(Integer idEmployee, Integer idPatient, String date) {
        this.idEmployee = idEmployee;
        this.idPatient = idPatient;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Integer getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Integer idPatient) {
        this.idPatient = idPatient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
