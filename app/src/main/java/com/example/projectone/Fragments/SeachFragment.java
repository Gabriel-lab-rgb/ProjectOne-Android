package com.example.projectone.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.projectone.Entity.Post;
import com.example.projectone.Entity.Usuario;
import com.example.projectone.Entity.UsuarioSummary;
import com.example.projectone.R;
import com.example.projectone.adapter.PostAdapter;
import com.example.projectone.adapter.UserAdapter;
import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeachFragment extends Fragment implements PostAdapter.ItemClickListener{

private SearchView searchView;
private EditText search;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private PostAdapter.ItemClickListener clickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_seach, container, false);
        /*searchView=view.findViewById(R.id.searchView);*/
        search = view.findViewById(R.id.search);
        clickListener=this;
        recyclerView=view.findViewById(R.id.user_recycler);
        /*search = searchView.findViewById(androidx.appcompat.R.id.search_src_text);*/
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Método invocado antes de que el texto cambie en el SearchView
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                obtenerUsuarios(s.toString());
            }

        });

        return view;
    }


    @Override
    public void onItemClick(Post post) {

    }

    @Override
    public void onUserClick(UsuarioSummary usuario) {

        ProfileFragment profile=ProfileFragment.newInstance(usuario.getUsername());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profile).addToBackStack(null).commit();
    }


    public void obtenerUsuarios(String cadena){

        Call<List<UsuarioSummary>> UserCall= ApiClient.getClientGson().create(ApiInterface.class).getUserStartingWith(cadena);

        UserCall.enqueue(new Callback<List<UsuarioSummary>>() {
            @Override
            public void onResponse(Call<List<UsuarioSummary>> call, Response<List<UsuarioSummary>> response) {
                if(response.isSuccessful()){
                    Log.i("c 94",response.body().toString());
                    userAdapter=new UserAdapter((ArrayList<UsuarioSummary>) response.body(),getActivity(),clickListener);
                    recyclerView.setAdapter(userAdapter);

                }else{
                    Log.i("c",String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<List<UsuarioSummary>> call, Throwable t) {
                Log.i("c",t.getMessage());
            }
        });
    }

    }
