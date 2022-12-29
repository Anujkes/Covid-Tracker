package com.example.covidtracker.retrofit;

import com.example.covidtracker.data.ApiData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface retrofitInterface {

    @GET("v1")
    Call<List<ApiData>> getdata();
}
