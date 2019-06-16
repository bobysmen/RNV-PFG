package com.example.rnv_pfg.ui.patient;

import android.app.Application;

import com.example.rnv_pfg.data.models.Patient;
import com.example.rnv_pfg.data.remote.ApiService;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;

public class PatientsViewModel extends ViewModel {

    private Patient patient;
    private MutableLiveData<List<Patient>> patients = new MutableLiveData<>();

    public void setPatients (List<Patient> listPatients){
        patients.postValue(listPatients);
    }

    public LiveData<List<Patient>> getPatients(){
        return patients;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
