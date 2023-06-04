package com.example.projectone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.projectone.utils.SharedPreferencesUtils;
import com.example.projectone.fragment.ChatsFragment;
import com.example.projectone.fragment.CreatePostFragment;
import com.example.projectone.fragment.HomeFragment;
import com.example.projectone.fragment.ProfileFragment;
import com.example.projectone.fragment.SeachFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    public BottomNavigationView navigationView;

    public static final String USERNAME_OR_EMAIL="user_key";
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username= SharedPreferencesUtils.getString(this, USERNAME_OR_EMAIL, null);
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
                SeachFragment activity=new SeachFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,activity).addToBackStack(null).commit();
                break;
            case R.id.profile:
                ProfileFragment profile=ProfileFragment.newInstance(username);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profile).addToBackStack(null).commit();
                break;
            case R.id.add:
                CreatePostFragment createPost=new CreatePostFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);
                transaction.replace(R.id.frame_layout,createPost).addToBackStack(null).commit();
                break;
            case R.id.messages:
                ChatsFragment chatsFragment=new ChatsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,chatsFragment).addToBackStack(null).commit();
                break;

        }

        return false;
    }
}