package com.example.rnv_pfg.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private TextInputEditText txtLogin;
    private TextInputEditText txtPassword;
    private Button btnLogin;
    private Button btnRegister;
    private TextInputLayout tilLogin;
    private TextInputLayout tilPassword;
    private LoginViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);
        setupViews(getView());
        fieldsValidations();
    }

    private void setupViews(View view) {
        tilLogin = ViewCompat.requireViewById(view, R.id.tilLogin);
        txtLogin = ViewCompat.requireViewById(view, R.id.txtLogin);
        tilPassword = ViewCompat.requireViewById(view, R.id.tilPassword);
        txtPassword = ViewCompat.requireViewById(view, R.id.txtPassword);
        btnLogin = ViewCompat.requireViewById(view, R.id.btnLogin);
        btnRegister = ViewCompat.requireViewById(view, R.id.btnRegister);

        btnRegister.setOnClickListener(v -> navigateRegister());
        btnLogin.setOnClickListener(v -> login());
    }

    private void fieldsValidations() {
        TextViewUtils.addAfterTextChangedListener(txtLogin, s -> checkEmail(txtLogin.getText().toString()));
        TextViewUtils.addAfterTextChangedListener(txtPassword, s -> checkString(txtPassword.getText().toString(), tilPassword));
    }

    //Metodos Validaciones
    private boolean checkEmail(String s) {
        if(!ValidationsUtils.isValidEmail(s)){
            tilLogin.setError(getString(R.string.msgError_main_form));
            return false;
        }else{
            tilLogin.setErrorEnabled(false);
            tilLogin.setError("");
            return true;
        }
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

    private void login() {
        //Si el formulario esta ok
        if (checkEmail(txtLogin.getText().toString()) && checkString(txtPassword.getText().toString(), tilPassword)) {
            Call<Employee> call = ApiService.getInstance(getContext()).getApi().login(txtLogin.getText().toString(), txtPassword.getText().toString());
            call.enqueue(new Callback<Employee>() {
                @Override
                public void onResponse(Call<Employee> call, Response<Employee> response) {
                    if(response.body() != null && response.isSuccessful()){
                        //Almacenar Empleado logueado
                        viewModel.setEmployee(response.body());
                        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_appointmentsDaily);
                    }else{
                        //TODO Mensajes de error en recursos de cadenas
                        Toast.makeText(getContext(),"El email o contraseña son incorrectos", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Employee> call, Throwable t) {
                    Toast.makeText(getContext(),"Error de conexion", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            checkEmail(txtLogin.getText().toString());
            checkString(txtPassword.getText().toString(), tilPassword);
        }
    }


    private void navigateRegister() {
        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_registerEmployee);
    }
}
