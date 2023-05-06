package com.example.projectone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.projectone.Fragments.ActivityFragment;
import com.example.projectone.Fragments.CreatePostFragment;
import com.example.projectone.Fragments.HomeFragment;
import com.example.projectone.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

  private BottomNavigationView navigationView;
  private ArrayList<Fragment> fragmentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new ActivityFragment());
        fragmentArrayList.add(new ProfileFragment());*/

        navigationView=findViewById(R.id.bottomNavigationView);
        navigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.community:
                HomeFragment home=new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,home).addToBackStack(null).commit();
                break;
            case R.id.activity:
                ActivityFragment activity=new ActivityFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,activity).addToBackStack(null).commit();
                break;
            case R.id.profile:
                ProfileFragment profile=new ProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profile).addToBackStack(null).commit();
                break;
            case R.id.add:
                CreatePostFragment createPost=new CreatePostFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,createPost).addToBackStack(null).commit();
                break;
            case R.id.messages:
                break;

        }

        return false;
    }
}