package com.example.rnv_pfg.ui.formAddPatient;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.data.models.Patient;
import com.example.rnv_pfg.data.remote.ApiService;
import com.example.rnv_pfg.utils.TextViewUtils;
import com.example.rnv_pfg.utils.ValidationsUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
    private TextInputLayout tilName;
    private TextInputLayout tilSurname;
    private TextInputLayout tilEmail;
    private TextInputLayout tilPhone;
    private TextInputLayout tilAddress;

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
        fieldsValidations();
    }

    //Validaciones
    private void fieldsValidations() {
        TextViewUtils.addAfterTextChangedListener(txtName, s -> checkString(txtName.getText().toString(), tilName));
        TextViewUtils.addAfterTextChangedListener(txtSurname, s -> checkString(txtSurname.getText().toString(), tilSurname));
        TextViewUtils.addAfterTextChangedListener(txtEmail, s -> checkEmail(txtEmail.getText().toString()));
        TextViewUtils.addAfterTextChangedListener(txtPhone, s -> checkPhone(txtPhone.getText().toString()));
        TextViewUtils.addAfterTextChangedListener(txtAddress, s -> checkString(txtAddress.getText().toString(), tilAddress));
    }

    private boolean checkString(String s, TextInputLayout textInputLayout) {
        if(TextUtils.isEmpty(s)){
            textInputLayout.setError(getString(R.string.msgError_main_form));
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
            textInputLayout.setError("");
            return true;
        }
    }

    private boolean checkEmail(String s) {
        if(!ValidationsUtils.isValidEmail(s)){
            tilEmail.setError(getString(R.string.msgError_main_form));
            return false;
        }else{
            tilEmail.setErrorEnabled(false);
            tilEmail.setError("");
            return true;
        }
    }

    private boolean checkPhone(String s) {
        if(!ValidationsUtils.isValidPhone(s)){
            tilPhone.setError(getString(R.string.msgError_main_form));
            return false;
        }else{
            tilPhone.setErrorEnabled(false);
            tilPhone.setError("");
            return true;
        }
    }

    private void setupViews(View view) {
        tilName = ViewCompat.requireViewById(view, R.id.tilName);
        txtName = ViewCompat.requireViewById(view, R.id.txtName);
        tilSurname = ViewCompat.requireViewById(view, R.id.tilSurname);
        txtSurname = ViewCompat.requireViewById(view, R.id.txtSurname);
        tilEmail = ViewCompat.requireViewById(view, R.id.tilEmail);
        txtEmail = ViewCompat.requireViewById(view, R.id.txtEmail);
        tilPhone = ViewCompat.requireViewById(view, R.id.tilPhone);
        txtPhone = ViewCompat.requireViewById(view, R.id.txtPhone);
        tilAddress = ViewCompat.requireViewById(view, R.id.tilAddress);
        txtAddress = ViewCompat.requireViewById(view, R.id.txtAddress);
        btnSave = ViewCompat.requireViewById(view, R.id.btnSavePatient);

        btnSave.setOnClickListener(v -> savePatient());
    }

    private void savePatient() {
        if (validForm()) {
            Patient patient = new Patient(txtName.getText().toString(), txtSurname.getText().toString(), txtEmail.getText().toString(), txtPhone.getText().toString(), txtAddress.getText().toString());
            Call<Patient> call = ApiService.getInstance(getContext()).getApi().addPatient(patient);
            call.enqueue(new Callback<Patient>() {
                @Override
                public void onResponse(Call<Patient> call, Response<Patient> response) {
                    if(response.body() != null && response.isSuccessful()){
                        Toast.makeText(getContext(),getString(R.string.onResponse_msgSaveSuccess), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getContext(),getString(R.string.call_msgNullResponse), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Patient> call, Throwable t) {
                    Toast.makeText(getContext(),getString(R.string.call_onFailure_msg), Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(getContext(), getString(R.string.formAddPatient_msgErrorValid), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validForm() {
        if(checkString(txtName.getText().toString(), tilName) && checkString(txtSurname.getText().toString(), tilSurname) &&
                checkEmail(txtEmail.getText().toString()) && checkPhone(txtPhone.getText().toString()) &&
                checkString(txtAddress.getText().toString(), tilAddress)){
            return true;
        }else{
            checkString(txtName.getText().toString(), tilName);
            checkString(txtSurname.getText().toString(), tilSurname);
            checkEmail(txtEmail.getText().toString());
            checkPhone(txtPhone.getText().toString());
            checkString(txtAddress.getText().toString(), tilAddress);
            return false;
        }
    }
}
