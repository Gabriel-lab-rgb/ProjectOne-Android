package com.example.projectone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.projectone.Entity.Post;
import com.example.projectone.Entity.Usuario;
import com.example.projectone.adapter.PostAdapter;
import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements View.OnClickListener {


    private ArrayList<Post> posts;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private ImageView createPost;

    private Usuario usuario;
    private BottomSheetDialog bottomSheetDialog;
    private View bottomSheetView;

    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFERENCES="shared_prefs";
    public static final String USERNAME_OR_EMAIL="user_key";
    String username;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        createPost=view.findViewById(R.id.create);
        createPost.setOnClickListener(this);
        bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.layout_bottom_sheet, view.findViewById(R.id.bottomSheetContainer));
        bottomSheetDialog.setContentView(bottomSheetView);

        recyclerView=view.findViewById(R.id.post_recycler);

        sharedPreferences=this.getActivity().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        username=sharedPreferences.getString(USERNAME_OR_EMAIL,null);
        getInfoUser();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create:
                bottomSheetView.findViewById(R.id.createPost).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CreatePostFragment createPost=new CreatePostFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,createPost).commit();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.show();

                break;

        }

    }
    public void getInfoUser(){

        Call<Usuario> UserCall= ApiClient.getClientGson().create(ApiInterface.class).getUser("Grangamer2018");

        UserCall.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Log.i("c",String.valueOf(response.body()));
                    postAdapter=new PostAdapter(response.body().getPosts(),getActivity().getApplicationContext());
                    recyclerView.setAdapter(postAdapter);
                    Log.i("c",String.valueOf(response.body().getPosts().get(0).getTexto()));
                }else{
                    Log.i("c",String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.i("c",t.getMessage());
            }
        });
    };
}