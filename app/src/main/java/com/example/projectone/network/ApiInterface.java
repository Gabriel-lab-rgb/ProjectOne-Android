package com.example.projectone.network;

import java.util.List;
import java.util.Map;

import com.example.projectone.entity.ChatMensaje;
import com.example.projectone.entity.Comentario;
import com.example.projectone.entity.Gif;
import com.example.projectone.entity.Sala;
import com.example.projectone.entity.Usuario;
import com.example.projectone.entity.UsuarioSummary;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @Multipart
    /*@Headers("Content-Type: multipart/form-data;boundary=12345")*/
    /*@FormUrlEncoded*/
    @POST("auth/signup")
    Call<String> userRegister(@Part("username") RequestBody username, @Part("email") RequestBody email, @Part("password") RequestBody password, @Part MultipartBody.Part imagen);

    @GET("/user/search")
    Call<List<UsuarioSummary>> getUserStartingWith(@Query("cadena") String cadena);


    //Perfil usuario
    @GET("user/{username}")
    Call<Usuario> getUser(@Path("username") String Username);

    //Crear publicacion
    @Multipart
    @POST("post/create")
    Call<String> CreatePost(@Part("username") RequestBody username,@Part("tipo") RequestBody tipo,@Part("texto") RequestBody contenido,@Part MultipartBody.Part media);


    @FormUrlEncoded
    @POST("post/addLike")
    Call<String> addLike(@Field("post_id") long id,@Field("username") String username);



    @DELETE("post/deleteLike")
    Call<String> deleteLike(@Query("post_id") long id,@Query("username") String username);

 @FormUrlEncoded
 @POST("user/follow/add")
 Call<String> addFollow(@Field("follower") String follower,@Field("followed") String followed);

 @FormUrlEncoded
 @HTTP(method = "DELETE", path = "user/follow/delete", hasBody = true)
 Call<String> deleteFollow(@Field("follower") String follower,@Field("followed") String followed);

    //Crear Comentario
    @FormUrlEncoded
    @POST("comment/create")
    Call<Comentario> createComment(@Field("post_id") long id,@Field("username") String username,@Field("comentario") String comentario);

    //Mostrar comentarios publicacion

    @GET("post/comments/{id}")
    Call<List<Comentario>> getCommentPost(@Path("id") long id);

    @GET("/{username}/salas")
    Call<List<Sala>> getSalas(@Path("username") String username);

    @GET("/salas/{salaId}/mensajes")
    Call<List<ChatMensaje>> getMensajes(@Path("salaId") long id);


}
