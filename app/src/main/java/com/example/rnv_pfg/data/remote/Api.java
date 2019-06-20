package com.example.rnv_pfg.data.remote;

import com.example.rnv_pfg.data.models.Appointment;
import com.example.rnv_pfg.data.models.Diagnosis;
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
    Call<Employee>login(@Field("email") String email,
                       @Field("password") String password);

    @POST("patient/all")
    Call<List<Patient>>all();

    @POST("patient/add")
    Call<Patient>addPatient(@Body Patient patient);

    @POST("appointment/add")
    Call<Appointment>addAppointment(@Body Appointment appointment);

    @FormUrlEncoded
    @POST("appointment/allPerPatient")
    Call<List<Appointment>>allPerPatient(@Field("patient") Integer patient);

    @FormUrlEncoded
    @POST("appointment/delete")
    Call<Boolean>deleteAppointment(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("diagnosis/getDiagnosis")
    Call<Diagnosis>getDiagnosis(@Field("appointment") Integer appointment);

    @POST("diagnosis/addDiagnosis")
    Call<Diagnosis>addDiagnosis(@Body Diagnosis diagnosis);

    @FormUrlEncoded
    @POST("diagnosis/delete")
    Call<Boolean>deleteDiagnosis(@Field("appointment") Integer appointment);

    @FormUrlEncoded
    @POST("patient/getPatientById")
    Call<Patient>getPatientById(@Field("patient") Integer patient);

    @FormUrlEncoded
    @POST("appointment/today")
    Call<List<Appointment>>allTodayAppointment(@Field("date") String date,
                                               @Field("employee") Integer employee);

    @FormUrlEncoded
    @POST("appointment/perDay")
    Call<List<Appointment>>perDay(@Field("date") String date,
                                  @Field("employee") Integer employee);

}
