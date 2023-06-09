package com.example.projectone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectone.utils.SharedPreferencesUtils;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private Button btnRegistro;

    private TextView logo;

    private static final String USERNAME="user_key";
    private static final String PASSWORD="password_key";
    private String usernameEmail,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btnLogin=this.findViewById(R.id.login);
        btnRegistro=this.findViewById(R.id.registro);
        logo=this.findViewById(R.id.logo);
        logo.setShadowLayer(4, 2, 2, Color.parseColor("#80000000"));

        btnLogin.setOnClickListener(this);
        btnRegistro.setOnClickListener(this);

      /*  usernameEmail= SharedPreferencesUtils.getString(this, USERNAME, null);
        password=SharedPreferencesUtils.getString(this, PASSWORD, null);*/


    }

    @Override
    protected void onStart() {
        super.onStart();
       /* if(usernameEmail!=null && password!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }*/
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.login:
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            break;

            case R.id.registro:
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                break;
        }
    }
}