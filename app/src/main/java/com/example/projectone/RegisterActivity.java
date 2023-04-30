package com.example.projectone;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editUsername;
    private EditText editPassword;
    private EditText editEmail;
    private ImageView avatar;
    private Button btn_register;
    public static final String SHARED_PREFERENCES="shared_prefs";
    public static final String USERNAME_OR_EMAIL="user_key";
    public static final String PASSWORD="password_key";
    SharedPreferences sharedPreferences;
    String usernameEmail,password;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);
                    avatar.setImageURI(uri);
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

        //SharedPreference
        sharedPreferences=getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        usernameEmail=sharedPreferences.getString(USERNAME_OR_EMAIL,null);
        password=sharedPreferences.getString(PASSWORD,null);

        //Evento clickListener
        avatar.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    public void register(){


        Map<String,String> params = new HashMap<String, String>();
        params.put("username",editUsername.getText().toString());
        params.put("email",editEmail.getText().toString());
        params.put("password",editPassword.getText().toString());
        params.put("file","");


        Call<String> create= ApiClient.getClientString().create(ApiInterface.class).CreatePost(params);
        create.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    Log.i("c","usuario insertado correctamente");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("c",t.getMessage());
            }
        });

    }


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