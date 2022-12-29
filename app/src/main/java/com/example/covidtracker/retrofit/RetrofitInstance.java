package com.example.covidtracker.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static Retrofit retrofit=null;
    private static String BASE_URL="https://covid-19.dataflowkit.com/";


    public static retrofitInterface getService()
    {
        if(retrofit==null)
        {
            retrofit =new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit.create(retrofitInterface.class);


    }
}
