package com.example.projectone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
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
        navigationView.setItemIconTintList(null);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        // Restaurar el icono original de los elementos no seleccionados
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.getItemId() != itemId) {
                menuItem.setIcon(getOriginalIcon(menuItem.getItemId()));
            }
        }

        switch (item.getItemId()){
            case R.id.principal:
                HomeFragment home=new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,home).commit();
                item.setIcon(R.drawable.baseline_home_24);
                break;
            case R.id.buscar:
                SeachFragment activity=new SeachFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,activity).commit();
                item.setIcon(R.drawable.baseline_search_24);
                break;
            case R.id.perfil:
                ProfileFragment profile=ProfileFragment.newInstance(username);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profile).commit();
                item.setIcon(R.drawable.baseline_person_24);
                break;
            case R.id.add:
                CreatePostFragment createPost=new CreatePostFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);
                transaction.replace(R.id.frame_layout,createPost).addToBackStack(null).commit();
                item.setIcon(R.drawable.baseline_add_to_photos_24);
                break;
            case R.id.mensajes:
                ChatsFragment chatsFragment=new ChatsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,chatsFragment).commit();
                item.setIcon(R.drawable.baseline_chat_bubble_24);
                break;

        }

        return true;
    }

    private Drawable getOriginalIcon(int itemId) {

        if (itemId == R.id.principal) {
            return getResources().getDrawable(R.drawable.outline_home_24);
        } else if (itemId == R.id.buscar) {
            return getResources().getDrawable(R.drawable.outline_search_24);
        }else if (itemId == R.id.perfil){
            return getResources().getDrawable(R.drawable.outline_person_outline_24);
        } else if (itemId == R.id.mensajes) {
            return getResources().getDrawable(R.drawable.outline_chat_bubble_outline_24);
        } else if (itemId == R.id.add) {
            return getResources().getDrawable(R.drawable.outline_library_add_24);
        }

        // Si no se encuentra el ID, retorna null o un icono por defecto
        return null;
    }
}