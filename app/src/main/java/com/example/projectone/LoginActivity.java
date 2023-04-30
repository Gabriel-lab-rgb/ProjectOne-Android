package com.example.projectone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectone.Form.LoginResponse;
import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_login;
    private EditText editUsername;
    private EditText editPassword;

    public TextView register;

    public static final String SHARED_PREFERENCES="shared_prefs";
    public static final String USERNAME_OR_EMAIL="user_key";
    public static final String PASSWORD="password_key";



    SharedPreferences sharedPreferences;
    String usernameEmail,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername=this.findViewById(R.id.editUsernameOrEmail);
        editPassword=this.findViewById(R.id.editPassword);
        btn_login=this.findViewById(R.id.button_login);
        register=this.findViewById(R.id.textCreate);
        btn_login.setOnClickListener(this);
        register.setOnClickListener(this);
        sharedPreferences=getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        usernameEmail=sharedPreferences.getString(USERNAME_OR_EMAIL,null);
        password=sharedPreferences.getString(PASSWORD,null);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(usernameEmail!=null && password!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_login) {
            if(editUsername.getText().toString().isEmpty() || editPassword.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Los campos no pueden estar vacio",Toast.LENGTH_LONG).show();
            }else{
                login();
            }
        }else if(v.getId() == R.id.textCreate){
            startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            finish();
        }
    }

    public void login(){

       /* LoginResponse login=new LoginResponse();
        login.setUsernameOrEmail(editUsername.getText().toString());
        login.setPassword(editPassword.getText().toString());
        Log.i("c",editUsername.getText().toString());
        Log.i("c",editPassword.getText().toString());*/

        Call<String> loginResponseCall= ApiClient.getClientString().create(ApiInterface.class).userLogin(editUsername.getText().toString(),editPassword.getText().toString());
        loginResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.i("c","hola");
                if(response.isSuccessful()){
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString(USERNAME_OR_EMAIL,editUsername.getText().toString());
                    editor.putString(PASSWORD,editPassword.getText().toString());
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }else{
                    Log.i("c",String.valueOf(response));
                    switch (response.code()){
                        case 403:
                            Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecta",Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(getApplicationContext(),"Error 500",Toast.LENGTH_LONG).show();
                            break;
                        case 404:
                            Toast.makeText(getApplicationContext(),"404",Toast.LENGTH_LONG).show();
                            break;
                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                Log.i("c",t.getMessage());
            }
        });
    }
}