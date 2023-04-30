package com.example.projectone.network;

import java.util.List;
import java.util.Map;

import com.example.projectone.Entity.Gif;
import com.example.projectone.Entity.Usuario;
import com.example.projectone.Form.LoginResponse;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("trending?api_key=41Z2BzErVCxm5tgg1bhzfckavjvNXK7h&limit=6&rating=r")
     Call<Gif> getTrending();

    //Login
    @FormUrlEncoded
    @POST("auth/signin")
    Call<String> userLogin(@Field("usernameOrEmail") String username,@Field("password") String password);

   //Registro del usuario
    @FormUrlEncoded
    @POST("auth/signup")
    Call<String> userRegister();

    //Perfil usuario
    @GET("user/{username}")
    Call<Usuario> getUser(@Path("username") String Username);

    //Crear publicacion
    @FormUrlEncoded
    @POST("post/create")
    Call<String> CreatePost(@FieldMap Map<String,String> params);

    //Crear Comentario
    @FormUrlEncoded
    @POST("comment/create")
    Call<String> CreateComment();

    //Mostrar comentarios publicacion
    @FormUrlEncoded
    @GET("post/comments/{id}")
    Call<String> ShowCommentPost(@Path("id") long id);
}
