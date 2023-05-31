package com.example.projectone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.projectone.Fragments.ChatsFragment;
import com.example.projectone.Fragments.CreatePostFragment;
import com.example.projectone.Fragments.HomeFragment;
import com.example.projectone.Fragments.ProfileFragment;
import com.example.projectone.Fragments.SeachFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

  private BottomNavigationView navigationView;
  private ArrayList<Fragment> fragmentArrayList;

    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFERENCES="shared_prefs";
    public static final String USERNAME_OR_EMAIL="user_key";
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new ActivityFragment());
        fragmentArrayList.add(new ProfileFragment());*/
        sharedPreferences=this.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        //username=sharedPreferences.getString(USERNAME_OR_EMAIL,null);
        username="Grangamer2018";
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