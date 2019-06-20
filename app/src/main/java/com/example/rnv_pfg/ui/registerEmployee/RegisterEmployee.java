package com.example.rnv_pfg.ui.registerEmployee;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.data.models.Employee;
import com.example.rnv_pfg.data.remote.ApiService;
import com.example.rnv_pfg.utils.TextViewUtils;
import com.example.rnv_pfg.utils.ValidationsUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterEmployee extends Fragment {

    private TextInputEditText txtName;
    private TextInputEditText txtSurName;
    private TextInputEditText txtEmail;
    private TextInputEditText txtPhone;
    private TextInputEditText txtPassword;
    private TextInputEditText txtConfirmPassword;
    private Button btnRegister;
    private TextInputLayout tilName;
    private TextInputLayout tilSurName;
    private TextInputLayout tilEmail;
    private TextInputLayout tilPhone;
    private TextInputLayout tilPassword;
    private TextInputLayout tilConfirmPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_employee_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(getView());
        fieldsValidations();
    }

    //Validacion de campos
    private void fieldsValidations() {
        TextViewUtils.addAfterTextChangedListener(txtName, s -> checkString(txtName.getText().toString(), tilName));
        TextViewUtils.addAfterTextChangedListener(txtSurName, s -> checkString(txtSurName.getText().toString(), tilSurName));
        TextViewUtils.addAfterTextChangedListener(txtEmail, s -> checkEmail(txtEmail.getText().toString()));
        TextViewUtils.addAfterTextChangedListener(txtPhone, s -> checkPhone(txtPhone.getText().toString()));
        TextViewUtils.addAfterTextChangedListener(txtPassword, s -> checkString(txtPassword.getText().toString(), tilPassword));
        TextViewUtils.addAfterTextChangedListener(txtConfirmPassword, s -> checkString(txtConfirmPassword.getText().toString(), tilConfirmPassword));
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
        tilSurName = ViewCompat.requireViewById(view, R.id.tilSurName);
        txtSurName = ViewCompat.requireViewById(view, R.id.txtSurName);
        tilEmail = ViewCompat.requireViewById(view, R.id.tilEmail);
        txtEmail = ViewCompat.requireViewById(view, R.id.txtEmail);
        tilPhone = ViewCompat.requireViewById(view, R.id.tilPhone);
        txtPhone = ViewCompat.requireViewById(view, R.id.txtPhone);
        tilPassword = ViewCompat.requireViewById(view, R.id.tilPassword);
        txtPassword = ViewCompat.requireViewById(view, R.id.txtPassword);
        tilConfirmPassword = ViewCompat.requireViewById(view, R.id.tilConfirmPassword);
        txtConfirmPassword = ViewCompat.requireViewById(view, R.id.txtConfirmPassword);
        btnRegister = ViewCompat.requireViewById(view, R.id.btnRegister);

        btnRegister.setOnClickListener(v -> addEmployee());
    }

    private void addEmployee() {
        if (validForm()) {
            if (txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {
                Employee employee = new Employee(txtName.getText().toString(), txtSurName.getText().toString(), txtEmail.getText().toString(), txtPhone.getText().toString(), txtPassword.getText().toString());

                Call<Employee> call = ApiService.getInstance(getContext()).getApi().addEmployee(employee);
                call.enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        if(response.body() != null && response.isSuccessful()){
                            Toast.makeText(getContext(),"El empleado" + response.body().getName() + "se agregro correctamente", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),"El empleado no se agrego correctamente", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        Toast.makeText(getContext(),"Error de conexion", Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Toast.makeText(getContext(), getString(R.string.resgisterEmployee_msgDiferentPass), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getContext(), getString(R.string.formAddPatient_msgErrorValid), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validForm() {
        if(checkString(txtName.getText().toString(), tilName) && checkString(txtSurName.getText().toString(), tilSurName) &&
                checkEmail(txtEmail.getText().toString()) && checkPhone(txtPhone.getText().toString()) &&
                checkString(txtPassword.getText().toString(), tilPassword) && checkString(txtConfirmPassword.getText().toString(), tilConfirmPassword)){
            return true;
        }else{
            checkString(txtName.getText().toString(), tilName);
            checkString(txtSurName.getText().toString(), tilSurName);
            checkEmail(txtEmail.getText().toString());
            checkPhone(txtPhone.getText().toString());
            checkString(txtPassword.getText().toString(), tilPassword);
            checkString(txtConfirmPassword.getText().toString(), tilConfirmPassword);
            return false;
        }
    }
}
