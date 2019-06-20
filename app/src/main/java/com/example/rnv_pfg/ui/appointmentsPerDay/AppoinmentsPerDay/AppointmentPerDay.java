package com.example.rnv_pfg.ui.appointmentsPerDay.AppoinmentsPerDay;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.data.models.Appointment;
import com.example.rnv_pfg.data.models.Patient;
import com.example.rnv_pfg.data.remote.ApiService;
import com.example.rnv_pfg.ui.appointmentsPerDay.FormAppointmentsPerDayViewModel;
import com.example.rnv_pfg.ui.patient.PatientsViewModel;
import com.example.rnv_pfg.ui.patient.listAppointmentPerPatient.ListAppointmentViewModel;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentPerDay extends Fragment {

    private AppointmentPerDayViewModel viewModel;
    private TextView lblEmptyView;
    private RecyclerView lstAppointment;
    private AppoinmentPerDayAdapter listAdapter;
    private FormAppointmentsPerDayViewModel viewModelFormPerDay;
    private PatientsViewModel viewModelPatient;
    private ListAppointmentViewModel viewModelAppointment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment_per_day, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity()).get(AppointmentPerDayViewModel.class);
        viewModelFormPerDay = ViewModelProviders.of(requireActivity()).get(FormAppointmentsPerDayViewModel.class);
        //Reutilizo los dos viewmodel siguientes para poder acceder a diagnosis desde distintos fragmentos
        viewModelAppointment = ViewModelProviders.of(requireActivity()).get(ListAppointmentViewModel.class);
        viewModelPatient = ViewModelProviders.of(requireActivity()).get(PatientsViewModel.class);
        setupViews(getView());
        callAppointmentPerDay();
        observeAppointmentPerDay();
    }

    private void observeAppointmentPerDay() {
        viewModel.getAppointments().observe(this, appointments -> {
            Collections.sort(appointments, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));

            listAdapter.submitList(appointments);
            lblEmptyView.setVisibility(appointments.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void callAppointmentPerDay() {
        Call<List<Appointment>> call = ApiService.getInstance(getContext()).getApi().perDay(viewModelFormPerDay.getDate(), 11);
        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if(response.body() != null && response.isSuccessful()){
                    viewModel.setAppointments(response.body());
                }else{
                    Toast.makeText(getContext(),"A ocurrido un error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {

            }
        });
    }

    private void setupViews(View view) {
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);
        lstAppointment = ViewCompat.requireViewById(view, R.id.lstAppointmentPerDay);

        lstAppointment.setHasFixedSize(true);
        listAdapter = new AppoinmentPerDayAdapter(position -> showDiagnosis(listAdapter.getItem(position)), position -> deleteAppointmentAndDiagnosis(listAdapter.getItem(position)));
        lstAppointment.setAdapter(listAdapter);
        lstAppointment.setLayoutManager(new GridLayoutManager(getContext(), 1));
        lstAppointment.setItemAnimator(new DefaultItemAnimator());
    }

    private void deleteAppointmentAndDiagnosis(Appointment appointment) {
        //Primero borro el diagnostico y despues la cita
        Call<Boolean> call = ApiService.getInstance(getContext()).getApi().deleteDiagnosis(appointment.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body() != null && response.isSuccessful()){
                    deleteAppointment(appointment);
                }else{
                    Toast.makeText(getContext(),"A ocurrido un error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    private void deleteAppointment(Appointment appointment) {
        Call<Boolean> call = ApiService.getInstance(getContext()).getApi().deleteAppointment(appointment.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body() != null && response.isSuccessful() && response.body()){
                    Toast.makeText(getContext(),"Appointment delete", Toast.LENGTH_LONG).show();
                    callAppointmentPerDay();
                }else{
                    Toast.makeText(getContext(),"A ocurrido un error,", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    private void showDiagnosis(Appointment appointment) {
        viewModelAppointment.setAppointment(appointment);
        Call<Patient> call = ApiService.getInstance(getContext()).getApi().getPatientById(appointment.getPatient());
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if(response.body() != null && response.isSuccessful()){
                    viewModelPatient.setPatient(response.body());
                    //Evito navegar con pacientes nulos
                    Navigation.findNavController(getView()).navigate(R.id.action_appointmentPerDay_to_diagnosisFragment);
                }else{
                    Toast.makeText(getContext(),"A ocurrido un error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(getContext(),"A ocurrido un error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
