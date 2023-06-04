package com.example.projectone.network;

import com.example.projectone.entity.Usuario;
import com.example.projectone.entity.UsuarioSummary;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    public static String API_BASE_URL="http://192.168.0.16:8080/";
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

    public static byte[] obtenerImagen(String imageName) throws IOException {
        final byte[][] imageBytes = {null};
        CountDownLatch latch = new CountDownLatch(1);

        Call<ResponseBody> call = ApiClient.getClientGson().create(ApiInterface.class).getImage(imageName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        imageBytes[0] = response.body().bytes();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                latch.countDown(); // Liberar el latch para indicar que se ha completado la llamada
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Manejar el caso de error de la llamada
                latch.countDown(); // Liberar el latch incluso en caso de error
            }
        });

        try {
            latch.await(); // Esperar hasta que se complete la llamada
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return imageBytes[0];
    }


}
