package com.example.rnv_pfg.ui.formAddAppointment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.base.DatePickerDialogFragment;
import com.example.rnv_pfg.base.TimePickerDialogFragment;
import com.example.rnv_pfg.data.models.Appointment;
import com.example.rnv_pfg.data.remote.ApiService;
import com.example.rnv_pfg.ui.patient.PatientsViewModel;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormAddAppointment extends Fragment {

    private TextInputEditText txtDate;
    private TextInputEditText txtTime;
    private Button btnSaveAppointment;
    private PatientsViewModel viewModelPatients;
    private TextView lblPatientName;
    private TextView lblPatientSurname;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_appointment_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModelPatients = ViewModelProviders.of(requireActivity()).get(PatientsViewModel.class);
        setupViews(getView());
    }

    private void setupViews(View view) {
        lblPatientName = ViewCompat.requireViewById(view, R.id.lblPatientName);
        lblPatientSurname = ViewCompat.requireViewById(view, R.id.lblPatientSurname);
        txtDate = ViewCompat.requireViewById(view, R.id.txtDate);
        txtTime = ViewCompat.requireViewById(view, R.id.txtTime);
        btnSaveAppointment = ViewCompat.requireViewById(view, R.id.btnSaveAppointment);

        lblPatientName.setText(viewModelPatients.getPatient().getName());
        lblPatientSurname.setText(viewModelPatients.getPatient().getSurname());

        txtDate.setOnClickListener(v -> showDatePickerDialog());
        txtTime.setOnClickListener(v -> showTimePickerDialog());
        btnSaveAppointment.setOnClickListener(v -> saveAppointment());
    }

    private void saveAppointment() {
        //TODO hacer comprobaciones de campos rellenos

        String date = txtDate.getText().toString() + " " + txtTime.getText().toString();
        Appointment appointment = new Appointment(11, viewModelPatients.getPatient().getId(), date);
        Call<Appointment> call = ApiService.getInstance(getContext()).getApi().addAppointment(appointment);
        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if(response.body() != null && response.isSuccessful()){
                    Toast.makeText(getContext(),"Insertado correctamente", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"A ocurrido un error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                Toast.makeText(getContext(),"Error Conexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showTimePickerDialog() {
        TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance((view, hourOfDay, minute) -> {
            String timePicker = " ";
            if(minute<10 && hourOfDay<10){
                timePicker = "0" + hourOfDay + ":0" + minute + ":00";
            }else if(minute<10){
                timePicker = hourOfDay + ":0" + minute + ":00";
            }else if(hourOfDay<10){
                timePicker = "0" + hourOfDay + ":" + minute + ":00";
            }else{
                timePicker = hourOfDay + ":" + minute + ":00";
            }

            txtTime.setText(timePicker);
        });
        timePickerDialogFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }

    private void showDatePickerDialog() {
        DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance((view, year, month, dayOfMonth) -> {
            String datePicker = "";
            if(dayOfMonth<10 && month+1<10){
                datePicker = year + "-0" + (month + 1) + "-0" + dayOfMonth;
            }else if(dayOfMonth<10){
                datePicker = year + "-" + (month + 1) + "-0" + dayOfMonth;
            }else if(month+1<10){
                datePicker = year + "-0" + (month + 1) + "-" + dayOfMonth;
            }else{
                datePicker = year + "-" + (month + 1) + "-" + dayOfMonth;
            }

            txtDate.setText(datePicker);
        });
        datePickerDialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}
