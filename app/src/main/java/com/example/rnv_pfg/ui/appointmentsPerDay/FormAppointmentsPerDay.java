package com.example.rnv_pfg.ui.appointmentsPerDay;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.base.DatePickerDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormAppointmentsPerDay extends Fragment {


    private TextInputEditText txtDate;
    private Button btnDoneDate;
    private FormAppointmentsPerDayViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_appointments_per_day, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity()).get(FormAppointmentsPerDayViewModel.class);
        setupViews(getView());
    }

    private void setupViews(View view) {
        txtDate = ViewCompat.requireViewById(view, R.id.txtDate);
        btnDoneDate = ViewCompat.requireViewById(view, R.id.btnDoneDate);

        txtDate.setOnClickListener(v -> showDatePickerDialog());
        btnDoneDate.setOnClickListener(v -> navigateToListAppointmentsPerDay());
    }

    private void navigateToListAppointmentsPerDay() {
        viewModel.setDate(txtDate.getText().toString());
        Navigation.findNavController(getView()).navigate(R.id.action_formAppointmentsPerDay_to_appointmentPerDay);
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
