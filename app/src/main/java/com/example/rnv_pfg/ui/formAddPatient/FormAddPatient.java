package com.example.rnv_pfg.ui.formAddPatient;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.data.models.Patient;
import com.example.rnv_pfg.data.remote.ApiService;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormAddPatient extends Fragment {


    private TextInputEditText txtName;
    private TextInputEditText txtSurname;
    private TextInputEditText txtEmail;
    private TextInputEditText txtPhone;
    private TextInputEditText txtAddress;
    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_add_patient, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(getView());
    }

    private void setupViews(View view) {
        txtName = ViewCompat.requireViewById(view, R.id.txtName);
        txtSurname = ViewCompat.requireViewById(view, R.id.txtSurname);
        txtEmail = ViewCompat.requireViewById(view, R.id.txtEmail);
        txtPhone = ViewCompat.requireViewById(view, R.id.txtPhone);
        txtAddress = ViewCompat.requireViewById(view, R.id.txtAddress);
        btnSave = ViewCompat.requireViewById(view, R.id.btnSavePatient);

        btnSave.setOnClickListener(v -> savePatient());
    }

    private void savePatient() {
        Patient patient = new Patient(txtName.getText().toString(), txtSurname.getText().toString(), txtEmail.getText().toString(), txtPhone.getText().toString(), txtAddress.getText().toString());
        Call<Patient> call = ApiService.getInstance(getContext()).getApi().addPatient(patient);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if(response.body() != null && response.isSuccessful()){
                    Toast.makeText(getContext(),"Insertado correctamente", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"A ocurrido un error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

            }
        });
    }
}
