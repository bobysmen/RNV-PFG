package com.example.rnv_pfg.ui.patient;


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

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Patients extends Fragment {


    private TextView lblEmptyView;
    private RecyclerView lstPatient;
    private PatientsAdapter listAdapter;
    private PatientsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_patients, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(PatientsViewModel.class);
        setupViews(getView());
        callPatients();
        observePatients();
    }

    private void observePatients() {
        viewModel.getPatients().observe(this, patients -> {

            Collections.sort(patients, (o1, o2) -> o1.getName().compareTo(o2.getName()));
            listAdapter.submitList(patients);
            lblEmptyView.setVisibility(patients.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void callPatients() {
        Call<List<Patient>> call = ApiService.getInstance(getContext()).getApi().all();
        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if(response.body() != null && response.isSuccessful()){
                    viewModel.setPatients(response.body());
                }else{
                    Toast.makeText(getContext(),"A ocurrido un error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                Toast.makeText(getContext(),"Error de conexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupViews(View view) {
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);
        lstPatient = ViewCompat.requireViewById(view, R.id.lstPatient);

        lstPatient.setHasFixedSize(true);
        listAdapter = new PatientsAdapter(position -> addAppointment(listAdapter.getItem(position)), position -> showListAppointments(listAdapter.getItem(position)), position -> showPatient(listAdapter.getItem(position)));
        lstPatient.setAdapter(listAdapter);
        lstPatient.setLayoutManager(new GridLayoutManager(getContext(), 1));
        lstPatient.setItemAnimator(new DefaultItemAnimator());
    }

    private void showPatient(Patient patient) {
        viewModel.setPatientToShow(patient);
        Navigation.findNavController(getView()).navigate(R.id.action_patients_to_detailsPatient);
    }

    private void showListAppointments(Patient patient) {
        viewModel.setPatient(patient);
        Navigation.findNavController(getView()).navigate(R.id.action_patients_to_listAppointmentPatient);
    }

    private void addAppointment(Patient patient) {
        viewModel.setPatientAdd(patient);
        Navigation.findNavController(getView()).navigate(R.id.action_patients_to_formAddAppointment);
    }
}
