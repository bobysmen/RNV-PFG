package com.example.rnv_pfg.ui.appointmentsPerDay;

import androidx.lifecycle.ViewModel;

public class FormAppointmentsPerDayViewModel extends ViewModel {

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
