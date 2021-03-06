package com.example.rnv_pfg.ui.diagnosis;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.data.models.Diagnosis;
import com.example.rnv_pfg.data.remote.ApiService;
import com.example.rnv_pfg.ui.patient.PatientsViewModel;
import com.example.rnv_pfg.ui.patient.listAppointmentPerPatient.ListAppointmentViewModel;
import com.example.rnv_pfg.utils.TextViewUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiagnosisFragment extends Fragment {


    private ListAppointmentViewModel viewModelAppointment;
    private PatientsViewModel viewModelPatient;
    private TextView lblName;
    private TextView lblSurname;
    private TextView lblDate;
    private EditText txtDescription;
    private EditText txtTreatment;
    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diagnosis, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelAppointment = ViewModelProviders.of(requireActivity()).get(ListAppointmentViewModel.class);
        viewModelPatient = ViewModelProviders.of(requireActivity()).get(PatientsViewModel.class);

        setupViews(getView());
        callDiagnosis();
    }

    private void callDiagnosis() {
        Call<Diagnosis> call = ApiService.getInstance(getContext()).getApi().getDiagnosis(viewModelAppointment.getAppointment().getId());
        call.enqueue(new Callback<Diagnosis>() {
            @Override
            public void onResponse(Call<Diagnosis> call, Response<Diagnosis> response) {
                if(response.body() != null && response.isSuccessful()){
                    fillField(response.body());
                }else{
                    Toast.makeText(getContext(),getString(R.string.call_msgNullResponse), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Diagnosis> call, Throwable t) {
                Toast.makeText(getContext(),getString(R.string.onFailure_getDiagnosis_msg), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillField(Diagnosis diagnosis) {
        txtDescription.setText(diagnosis.getDescription());
        txtTreatment.setText(diagnosis.getTreatment());
    }

    private void setupViews(View view) {
        lblName = ViewCompat.requireViewById(view, R.id.lblName);
        lblSurname = ViewCompat.requireViewById(view, R.id.lblSurname);
        lblDate = ViewCompat.requireViewById(view, R.id.lblDate);
        txtDescription = ViewCompat.requireViewById(view, R.id.txtDescription);
        txtTreatment = ViewCompat.requireViewById(view, R.id.txtTreatment);
        btnSave = ViewCompat.requireViewById(view, R.id.btnSaveDiagnosis);

        btnSave.setOnClickListener(v -> saveDiagnosis());

        lblName.setText(viewModelPatient.getPatient().getName());
        lblSurname.setText(viewModelPatient.getPatient().getSurname());
        lblDate.setText(viewModelAppointment.getAppointment().getDate());
    }

    private void saveDiagnosis() {
        if (!TextUtils.isEmpty(txtDescription.getText().toString()) && !TextUtils.isEmpty(txtTreatment.getText().toString())) {
            Diagnosis diagnosis = new Diagnosis(txtDescription.getText().toString(), txtTreatment.getText().toString(), viewModelAppointment.getAppointment().getId());
            Call<Diagnosis> call = ApiService.getInstance(getContext()).getApi().addDiagnosis(diagnosis);
            call.enqueue(new Callback<Diagnosis>() {
                @Override
                public void onResponse(Call<Diagnosis> call, Response<Diagnosis> response) {
                    if(response.body() != null && response.isSuccessful()){
                        Toast.makeText(getContext(),getString(R.string.onResponse_msgSaveSuccess), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getContext(),getString(R.string.call_msgNullResponse), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Diagnosis> call, Throwable t) {
                    Toast.makeText(getContext(),getString(R.string.call_onFailure_msg), Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(getContext(), getString(R.string.formPerDay_msgValidationDate), Toast.LENGTH_LONG).show();
        }
    }
}
