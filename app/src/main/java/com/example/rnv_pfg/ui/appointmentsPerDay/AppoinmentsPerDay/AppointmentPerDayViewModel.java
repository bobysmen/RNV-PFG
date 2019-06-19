package com.example.rnv_pfg.ui.appointmentsPerDay.AppoinmentsPerDay;

import com.example.rnv_pfg.data.models.Appointment;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AppointmentPerDayViewModel extends ViewModel {

    private MutableLiveData<List<Appointment>> appointments = new MutableLiveData<>();

    public LiveData<List<Appointment>> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> listAppointments) {
        appointments.postValue(listAppointments);
    }
}
