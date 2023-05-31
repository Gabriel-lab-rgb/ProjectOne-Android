package com.example.projectone.network;

import com.example.projectone.Entity.Usuario;
import com.example.projectone.Entity.UsuarioSummary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
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


    public static ApiInterface getApiInterfaceString() {
        return getClientString().create(ApiInterface.class);
    }

    public static ApiInterface getApiInterfaceGson() {
        return getClientGson().create(ApiInterface.class);
    }

    public static void obtenerUsuarios(String cadena, Callback<List<UsuarioSummary>> callback) {
        ApiInterface apiInterface = getApiInterfaceGson();
        Call<List<UsuarioSummary>> userCall = apiInterface.getUserStartingWith(cadena);
        userCall.enqueue(callback);
    }

    public static void obtenerDatosUsuario(String username, Callback<Usuario> callback){
        ApiInterface apiInterface=getApiInterfaceGson();
        Call<Usuario> userCall= apiInterface.getUser(username);
        userCall.enqueue(callback);

    }

}
