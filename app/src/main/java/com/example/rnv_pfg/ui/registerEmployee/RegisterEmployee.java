package com.example.rnv_pfg.ui.registerEmployee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.data.models.Employee;
import com.example.rnv_pfg.data.remote.ApiService;
import com.google.android.material.textfield.TextInputEditText;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_employee_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(getView());
    }

    private void setupViews(View view) {
        txtName = ViewCompat.requireViewById(view, R.id.txtName);
        txtSurName = ViewCompat.requireViewById(view, R.id.txtSurName);
        txtEmail = ViewCompat.requireViewById(view, R.id.txtEmail);
        txtPhone = ViewCompat.requireViewById(view, R.id.txtPhone);
        txtPassword = ViewCompat.requireViewById(view, R.id.txtPassword);
        txtConfirmPassword = ViewCompat.requireViewById(view, R.id.txtConfirmPassword);
        btnRegister = ViewCompat.requireViewById(view, R.id.btnRegister);

        btnRegister.setOnClickListener(v -> addEmployee());
    }

    private void addEmployee() {
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
    }
}
