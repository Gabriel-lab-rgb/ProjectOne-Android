package com.example.projectone.Fragments;

import static com.example.projectone.MainActivity.USERNAME_OR_EMAIL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.projectone.Custom.SharedPreferencesUtils;
import com.example.projectone.Entity.Follow;
import com.example.projectone.Entity.LikePost;
import com.example.projectone.Entity.Post;
import com.example.projectone.Entity.Usuario;
import com.example.projectone.Entity.UsuarioSummary;
import com.example.projectone.R;
import com.example.projectone.adapter.PostAdapter;
import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;
import com.example.projectone.network.UserInfoCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements View.OnClickListener,PostAdapter.ItemClickListener {


    private ArrayList<Post> posts;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private ImageView createPost;

    private Usuario usuario;

    private String currentUsername;
    private BottomSheetDialog bottomSheetDialog;
    private Button seguir;
    private View bottomSheetView;
    private PostAdapter.ItemClickListener clickListener;




    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;


    public static ProfileFragment newInstance(String param1) {
        ProfileFragment fragment = new ProfileFragment ();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 =getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        currentUsername= SharedPreferencesUtils.getString(getActivity(), USERNAME_OR_EMAIL, null);
        createPost=view.findViewById(R.id.create);
        createPost.setOnClickListener(this);
        seguir=view.findViewById(R.id.btn_seguir);
        seguir.setOnClickListener(this);
        bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_bottom_sheet, view.findViewById(R.id.bottomSheetContainer));
        bottomSheetDialog.setContentView(bottomSheetView);

        recyclerView=view.findViewById(R.id.post_recycler);
        clickListener=this;


        getInfoUser(new UserInfoCallback() {
            @Override
            public void onUserInfoReceived(Usuario user) {
                usuario=user;
            }

            @Override
            public void onUserInfoError(Throwable t) {
            }
        });
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
            case R.id.btn_seguir:

                boolean exists = false;
                Follow followToRemove = null;
                for (Follow follow : usuario.getSeguidores()) {
                    if (follow.getFollower() != null && currentUsername.equals(follow.getFollower().getUsername())) {
                        exists = true;
                        followToRemove = follow;
                        break;
                    }
                }

                if (exists) {
                    usuario.getSiguiendo().remove(followToRemove);
                  /*  deleteSeguidor();*/
                } else {
                    Follow follow = new Follow();
                    usuario.getSiguiendo().add(follow);
                   /* post.getLikePosts().add(likePostToAdd);*/
                   /* addSeguidor();*/
                }

               break;

        }

    }
    public void getInfoUser(UserInfoCallback callback){



        Call<Usuario> UserCall= ApiClient.getClientGson().create(ApiInterface.class).getUser(mParam1);

        UserCall.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Log.i("c",String.valueOf(response.body()));

                    Usuario usuario=response.body();
                    /*Log.i("c",user[0].toString());*/
                    if(response.body().getPosts().size()!=0){
                        postAdapter=new PostAdapter(response.body().getPosts(),currentUsername,getActivity(),clickListener );
                        recyclerView.setAdapter(postAdapter);

                       /* Log.i("c",String.valueOf(response.body().getPosts().get(0).getTexto()));*/
                    }
                    callback.onUserInfoReceived(usuario);
                }else{
                    Log.i("c",String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.i("c",t.getMessage());
                callback.onUserInfoError(t);
            }
        });

    }


    public void addSeguidor(){

        Call<String> create= ApiClient.getClientString().create(ApiInterface.class).addFollow(currentUsername,mParam1);
        create.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    Log.i("c","siguiendo a usuario " + mParam1);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("c",t.getMessage());
            }
        });
    }

    public void deleteSeguidor(){

        Call<String> create= ApiClient.getClientString().create(ApiInterface.class).deleteFollow(currentUsername,mParam1);
        create.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    Log.i("c","dejando de seguir a usuario " + mParam1);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("c",t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(Post post) {

        CommentsFragment home=CommentsFragment.newInstance(post.getId());
       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,home).addToBackStack(null).commit();

    }

    @Override
    public void onUserClick(UsuarioSummary usuario) {

        ProfileFragment profile=ProfileFragment.newInstance(usuario.getUsername());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profile).addToBackStack(null).commit();
    }
}