package com.example.rnv_pfg.ui.appointmentsDaily;


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
import com.example.rnv_pfg.ui.login.LoginViewModel;
import com.example.rnv_pfg.ui.mainActivity.MainActivity;
import com.example.rnv_pfg.ui.patient.PatientsViewModel;
import com.example.rnv_pfg.ui.patient.listAppointmentPerPatient.ListAppointmentViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentsDaily extends Fragment {

    private AppointmentDailyViewModel viewModel;
    private TextView lblEmptyView;
    private RecyclerView lstAppointment;
    private AppointmentDailyAdapter listAdapter;
    private ListAppointmentViewModel viewModelAppointment;
    private PatientsViewModel viewModelPatient;
    private LoginViewModel viewModelLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointments_daily, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Show BottomNav
        MainActivity.showBottomNav(getActivity());
        //Empleado Logueado
        viewModelLogin = ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);
        viewModel = ViewModelProviders.of(requireActivity()).get(AppointmentDailyViewModel.class);
        viewModelAppointment = ViewModelProviders.of(requireActivity()).get(ListAppointmentViewModel.class);
        viewModelPatient = ViewModelProviders.of(requireActivity()).get(PatientsViewModel.class);
        setupViews(getView());
        callAppointmentDaily();
        observeAppointmentDaily();

    }

    private void observeAppointmentDaily() {
        viewModel.getAppointments().observe(this, appointments -> {

            Collections.sort(appointments, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));

            listAdapter.submitList(appointments);
            lblEmptyView.setVisibility(appointments.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void callAppointmentDaily() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());

        Call<List<Appointment>> call = ApiService.getInstance(getContext()).getApi().allTodayAppointment(date, viewModelLogin.getEmployee().getId());
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
        lstAppointment = ViewCompat.requireViewById(view, R.id.lstAppointmentsDaily);

        lstAppointment.setHasFixedSize(true);
        listAdapter = new AppointmentDailyAdapter(position -> showDiagnosis(listAdapter.getItem(position)), position -> deleteAppointmentAndDiagnosis(listAdapter.getItem(position)));
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
                    callAppointmentDaily();
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
                    Navigation.findNavController(getView()).navigate(R.id.action_appointmentsDaily_to_diagnosisFragment);
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
