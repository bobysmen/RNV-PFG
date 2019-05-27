package com.example.rnv_pfg.data.remote;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private final String BASE_URL = "https://rnv-pfg.herokuapp.com/";

    private static ApiService instance;
    private Api api;

    public static synchronized ApiService getInstance(Context context){
        if(instance == null){
            instance = new ApiService(context.getApplicationContext());
        }
        return instance;
    }

    private ApiService(Context context){
        buildApiService(context);
    }

    private void buildApiService(Context context) {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }
}
