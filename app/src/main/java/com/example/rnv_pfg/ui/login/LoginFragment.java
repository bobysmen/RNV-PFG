package com.example.rnv_pfg.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rnv_pfg.R;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
    }

    private void navigateRegister() {
        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_registerEmployee);
    }
}
