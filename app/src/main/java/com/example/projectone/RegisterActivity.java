package com.example.projectone;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;
import com.google.android.material.resources.TextAppearance;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editUsername;
    private EditText editPassword;
    private EditText editEmail;
    private ImageView avatar;
    private Button btn_register;

    private TextView login;
    public static final String SHARED_PREFERENCES="shared_prefs";
    public static final String USERNAME_OR_EMAIL="user_key";
    public static final String PASSWORD="password_key";
    SharedPreferences sharedPreferences;
    String usernameEmail,password;
    Bitmap bitmap;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);
                    avatar.setImageURI(uri);

                    try {
                        bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        Log.d("c",bitmap.toString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Componentes del Activity
        editUsername=this.findViewById(R.id.register_username);
        editPassword=this.findViewById(R.id.register_password);
        editEmail=this.findViewById(R.id.register_email);
        avatar=this.findViewById(R.id.register_avatar);
        btn_register=this.findViewById(R.id.btn_register);
        login=this.findViewById(R.id.textLogin);

        //SharedPreference
        sharedPreferences=getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        usernameEmail=sharedPreferences.getString(USERNAME_OR_EMAIL,null);
        password=sharedPreferences.getString(PASSWORD,null);

        //Evento clickListener
        avatar.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String convertirImgString(Bitmap bitmap){

        ByteArrayOutputStream b=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,b);
        byte[] imagenBytes=b.toByteArray();
        String encodedImagen= Base64.getEncoder().encodeToString(imagenBytes);

        return encodedImagen;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void register(){



        Log.i("c",convertirImgString(bitmap)) ;
        Call<String> create= ApiClient.getClientString().create(ApiInterface.class).userRegister(editUsername.getText().toString(),editEmail.getText().toString(),editPassword.getText().toString(),convertirImgString(bitmap));
        create.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    Log.i("c","usuario insertado correctamente");
                }else{
                    Log.i("c", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("c",t.getMessage());
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_register) {
            if(editUsername.getText().toString().isEmpty() || editPassword.getText().toString().isEmpty() || editEmail.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Los campos no pueden estar vacio",Toast.LENGTH_LONG).show();
            }else{
                register();
            }
        }else if(v.getId() == R.id.register_avatar){
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());

        } else if (v.getId() == R.id.textLogin) {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
    }
}