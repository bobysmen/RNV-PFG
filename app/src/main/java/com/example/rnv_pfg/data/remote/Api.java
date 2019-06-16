package com.example.rnv_pfg.data.remote;

import com.example.rnv_pfg.data.models.Appointment;
import com.example.rnv_pfg.data.models.Employee;
import com.example.rnv_pfg.data.models.Patient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @POST("employee/add")
    Call<Employee>addEmployee(@Body Employee employee);

    @FormUrlEncoded
    @POST("employee/login")
    Call<Boolean>login(@Field("email") String email,
                       @Field("password") String password);

    @POST("patient/all")
    Call<List<Patient>>all();

    @POST("appointment/add")
    Call<Appointment>addAppointment(@Body Appointment appointment);

}
