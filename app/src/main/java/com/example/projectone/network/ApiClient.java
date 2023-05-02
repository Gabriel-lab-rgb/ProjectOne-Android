package com.example.projectone.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static String API_BASE_URL="http://192.168.0.16:8080/";
    private  static String API_BASE_GIF="https://api.giphy.com/v1/gifs/";
    private static Retrofit retrofit;
    public static Retrofit getApi(){
        retrofit =new Retrofit.Builder()
                .baseUrl(API_BASE_GIF)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public static Retrofit getClientString(){
        retrofit =new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit getClientGson(){
        retrofit =new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

}
