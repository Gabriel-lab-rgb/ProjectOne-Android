package com.example.projectone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectone.utils.SharedPreferencesUtils;
import com.example.projectone.utils.SnackbarUtils;
import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_login;
    private EditText editUsername;
    private EditText editPassword;
    private TextView register;
    private static final String USERNAME_OR_EMAIL="user_key";
    private static final String PASSWORD="password_key";
    private String usernameEmail,password;

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

        usernameEmail= SharedPreferencesUtils.getString(this, USERNAME_OR_EMAIL, null);
        password=SharedPreferencesUtils.getString(this, PASSWORD, null);


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
                SnackbarUtils.showLongSnackbar(findViewById(android.R.id.content), "Hay campos vacios", R.color.warning);
            }else{
                login();
            }
        }else if(v.getId() == R.id.textCreate){
            startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            finish();
        }

    }



    public void login(){

        Call<String> loginResponseCall= ApiClient.getClientString().create(ApiInterface.class).userLogin(editUsername.getText().toString(),editPassword.getText().toString());
        loginResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if(response.isSuccessful()){
                    SharedPreferencesUtils.setString(getApplicationContext(), USERNAME_OR_EMAIL, editUsername.getText().toString());
                    SharedPreferencesUtils.setString(getApplicationContext(), PASSWORD, editPassword.getText().toString());
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }else{
                    if(response.code()==403)
                        SnackbarUtils.showLongSnackbar(findViewById(android.R.id.content), "Usuario o contraseña incorrecta", R.color.error);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                SnackbarUtils.showLongSnackbar(findViewById(android.R.id.content), "Se ha producido un error de conexión", R.color.error);
                Log.i("c",t.getMessage());
            }
        });
    }
}