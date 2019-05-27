package com.example.rnv_pfg.ui.registerEmployee;

import com.example.rnv_pfg.data.remote.Api;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RegisterEmployeeViewModelFactory implements ViewModelProvider.Factory {

    private final Api api;

    public RegisterEmployeeViewModelFactory(Api api) {
        this.api = api;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RegisterEmployeeViewModel(api);
    }

}
