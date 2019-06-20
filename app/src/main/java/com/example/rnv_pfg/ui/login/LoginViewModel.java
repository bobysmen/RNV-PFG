package com.example.rnv_pfg.ui.login;

import com.example.rnv_pfg.data.models.Employee;

import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
