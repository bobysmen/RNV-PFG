package com.example.rnv_pfg.data.remote;

import com.example.rnv_pfg.data.models.Employee;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {

    @POST("employee/add")
    Call<Employee>addEmployee(@Body Employee employee);

}
