package com.example.projectone.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectone.R;
import com.example.projectone.adapter.PostAdapter;
import com.example.projectone.adapter.SalaAdapter;
import com.example.projectone.adapter.UserAdapter;
import com.example.projectone.entity.Sala;
import com.example.projectone.entity.Usuario;
import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;
import com.example.projectone.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatsFragment extends Fragment implements SalaAdapter.SalaClickListener {

    private String username;
    private RecyclerView recyclerView;
    private SalaAdapter salaAdapter;

    private SalaAdapter.SalaClickListener salaClickListener;
    private static final String USERNAME_OR_EMAIL="user_key";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chats, container, false);
        // Inflate the layout for this fragment
        username=SharedPreferencesUtils.getString(getActivity(), USERNAME_OR_EMAIL, null);
        recyclerView=view.findViewById(R.id.chats_recycler);
        obtenerSalas();
        return view;
    }


    public void obtenerSalas(){

        Call<List<Sala>> f = ApiClient.getClientGson().create(ApiInterface.class).getSalas(username);
        f.enqueue(new Callback<List<Sala>>() {
            @Override
            public void onResponse(Call<List<Sala>> call, Response<List<Sala>> response) {
                if (response.isSuccessful()) {
                    List<Sala> salas = response.body();
                    salaAdapter=new SalaAdapter((ArrayList<Sala>) salas,username,salaClickListener,getActivity());
                    recyclerView.setAdapter(salaAdapter);
                    Log.i("c","bien");

                }else{
                    Log.i("c","error");
                }
            }

            @Override
            public void onFailure(Call<List<Sala>> call, Throwable t) {
                // Maneja el error de la solicitud
                Log.i("c",t.getMessage());
            }
        });

}

    @Override
    public void onItemClick(Sala sala) {
        ChatFragment chatFragment=new ChatFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,chatFragment).addToBackStack(null).commit();
    }
}