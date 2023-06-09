package com.example.projectone.fragment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.projectone.entity.Post;
import com.example.projectone.entity.UsuarioSummary;
import com.example.projectone.R;
import com.example.projectone.adapter.PostAdapter;
import com.example.projectone.adapter.UserAdapter;
import com.example.projectone.network.ApiClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeachFragment extends Fragment implements PostAdapter.ItemClickListener{

private EditText search;
   private ConstraintLayout noResults;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ProgressBar progressBar;
    private ArrayList<UsuarioSummary> listadoUsuarios;

    private PostAdapter.ItemClickListener clickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_seach, container, false);
        search = view.findViewById(R.id.search);
        noResults = view.findViewById(R.id.no_results_text);
        clickListener=this;
        recyclerView=view.findViewById(R.id.user_recycler);
        progressBar=view.findViewById(R.id.progressBar);
        /*search = searchView.findViewById(androidx.appcompat.R.id.search_src_text);*/
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Método invocado antes de que el texto cambie en el SearchView
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                progressBar.setVisibility(View.VISIBLE);
                noResults.setVisibility(View.GONE);
                if (s.length() > 0) {
                    ApiClient.obtenerUsuarios(s.toString(), new Callback<List<UsuarioSummary>>() {
                        @Override
                        public void onResponse(Call<List<UsuarioSummary>> call, Response<List<UsuarioSummary>> response) {
                            if(response.isSuccessful()){
                                noResults.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                listadoUsuarios=(ArrayList<UsuarioSummary>) response.body();
                                if(!listadoUsuarios.isEmpty()){
                                    userAdapter=new UserAdapter(listadoUsuarios,getActivity(),clickListener);
                                    recyclerView.setAdapter(userAdapter);
                                }else{
                                    vaciaAdapter();
                                }
                            }

                        }
                        @Override
                        public void onFailure(Call<List<UsuarioSummary>> call, Throwable t) {
                              Log.i("c",t.getMessage().toString());
                            Snackbar snackbar = Snackbar.make(view, "Fallo al conectar con el servidor", Snackbar.LENGTH_LONG);
                            snackbar.setBackgroundTint(getResources().getColor(R.color.error));
                            snackbar.show();
                            vaciaAdapter();
                        }
                    });
                } else {
                    vaciaAdapter();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
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

    private void vaciaAdapter(){
        if(listadoUsuarios!=null){
            listadoUsuarios.clear();
            userAdapter.notifyDataSetChanged();
        }
        noResults.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }


    }
