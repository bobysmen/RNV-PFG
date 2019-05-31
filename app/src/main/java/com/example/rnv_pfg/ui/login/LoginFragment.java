package com.example.rnv_pfg.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.data.remote.ApiService;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private TextInputEditText txtLogin;
    private TextInputEditText txtPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(getView());
    }

    private void setupViews(View view) {
        txtLogin = ViewCompat.requireViewById(view, R.id.txtLogin);
        txtPassword = ViewCompat.requireViewById(view, R.id.txtPassword);
        btnLogin = ViewCompat.requireViewById(view, R.id.btnLogin);
        btnRegister = ViewCompat.requireViewById(view, R.id.btnRegister);

        btnRegister.setOnClickListener(v -> navigateRegister());
        btnLogin.setOnClickListener(v -> login());
    }

    private void login() {
        Call<Boolean> call = ApiService.getInstance(getContext()).getApi().login(txtLogin.getText().toString(), txtPassword.getText().toString());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body() != null && response.isSuccessful() && response.body()){
                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_appointments);
                }else{
                    Toast.makeText(getContext(),"El email o contraseña son incorrectos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(),"Error de conexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateRegister() {
        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_registerEmployee);
    }
}
