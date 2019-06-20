package com.example.rnv_pfg.ui.patient.listAppointmentPerPatient;


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
import com.example.rnv_pfg.data.remote.ApiService;
import com.example.rnv_pfg.ui.patient.PatientsViewModel;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAppointmentPatient extends Fragment {


    private TextView lblEmptyView;
    private RecyclerView lstAppointment;
    private ListAppointmentPatientAdapter listAdapter;
    private ListAppointmentViewModel viewModel;
    private PatientsViewModel viewModelPatient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_appointment_patient, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(ListAppointmentViewModel.class);
        viewModelPatient = ViewModelProviders.of(getActivity()).get(PatientsViewModel.class);
        setupViews(getView());
        callAppointment();
        observeAppointments();
    }

    private void observeAppointments() {
        viewModel.getAppointments().observe(this, appointments -> {

            Collections.sort(appointments, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));

            listAdapter.submitList(appointments);
            lblEmptyView.setVisibility(appointments.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void callAppointment() {
        Call<List<Appointment>> call = ApiService.getInstance(getContext()).getApi().allPerPatient(viewModelPatient.getPatient().getId());
        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if(response.body() != null && response.isSuccessful()){
                    viewModel.setAppointments(response.body());
                }else{
                    Toast.makeText(getContext(),getString(R.string.call_msgNullResponse), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                Toast.makeText(getContext(),getString(R.string.call_onFailure_msg), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupViews(View view) {
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);
        lstAppointment = ViewCompat.requireViewById(view, R.id.lstAppointments);

        lstAppointment.setHasFixedSize(true);
        listAdapter = new ListAppointmentPatientAdapter(position -> showDiagnosis(listAdapter.getItem(position)), position -> deleteAppointmentAndDiagnosis(listAdapter.getItem(position)));
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
                    Toast.makeText(getContext(),getString(R.string.call_msgNullResponse), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(),getString(R.string.call_onFailure_msg), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteAppointment(Appointment appointment) {
        Call<Boolean> call = ApiService.getInstance(getContext()).getApi().deleteAppointment(appointment.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body() != null && response.isSuccessful() && response.body()){
                    Toast.makeText(getContext(),getString(R.string.onResponse_success_appintmentDelete), Toast.LENGTH_LONG).show();
                    callAppointment();
                }else{
                    Toast.makeText(getContext(),getString(R.string.call_msgNullResponse), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(),getString(R.string.call_onFailure_msg), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showDiagnosis(Appointment appointment) {
        viewModel.setAppointment(appointment);
        Navigation.findNavController(getView()).navigate(R.id.action_listAppointmentPatient_to_diagnosisFragment);
    }
}
