package com.example.projectone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

  private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView=findViewById(R.id.bottomNavigationView);
        navigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.community:
                HomeFragment home=new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,home).commit();
                break;
            case R.id.activity:
                ActivityFragment activity=new ActivityFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,activity).commit();
                break;
            case R.id.profile:
                ProfileFragment profile=new ProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profile).commit();
                break;
            case R.id.messages:
                break;

        }

        return false;
    }
}