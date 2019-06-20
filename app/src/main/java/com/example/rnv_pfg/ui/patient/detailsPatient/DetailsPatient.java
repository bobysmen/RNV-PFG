package com.example.rnv_pfg.ui.patient.detailsPatient;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.ui.patient.PatientsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsPatient extends Fragment {


    private TextView lblName;
    private TextView lblSurname;
    private TextView lblEmail;
    private TextView lblPhone;
    private TextView lblAddress;
    private PatientsViewModel viewModelPatients;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_patient, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelPatients = ViewModelProviders.of(getActivity()).get(PatientsViewModel.class);
        setupsViews(getView());
    }

    private void setupsViews(View view) {
        lblName = ViewCompat.requireViewById(view, R.id.lblName);
        lblSurname = ViewCompat.requireViewById(view, R.id.lblSurname);
        lblEmail = ViewCompat.requireViewById(view, R.id.lblEmail);
        lblPhone = ViewCompat.requireViewById(view, R.id.lblPhone);
        lblAddress = ViewCompat.requireViewById(view, R.id.lblAddress);

        if(viewModelPatients.getPatientToShow() != null){
            lblName.setText(viewModelPatients.getPatientToShow().getName());
            lblSurname.setText(viewModelPatients.getPatientToShow().getSurname());
            lblEmail.setText(viewModelPatients.getPatientToShow().getEmail());
            lblPhone.setText(viewModelPatients.getPatientToShow().getPhone());
            lblAddress.setText(viewModelPatients.getPatientToShow().getAddress());
        }
    }
}
