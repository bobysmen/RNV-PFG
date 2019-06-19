package com.example.rnv_pfg.ui.appointmentsDaily;

import com.example.rnv_pfg.data.models.Appointment;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AppointmentDailyViewModel extends ViewModel {

    private Appointment appointment;
    private MutableLiveData<List<Appointment>> appointments = new MutableLiveData<>();

    public LiveData<List<Appointment>> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> listAppointments) {
        appointments.postValue(listAppointments);
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
