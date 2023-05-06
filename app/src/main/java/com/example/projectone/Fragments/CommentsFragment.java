package com.example.projectone.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.projectone.Entity.Comentario;
import com.example.projectone.Entity.Usuario;
import com.example.projectone.R;
import com.example.projectone.adapter.CommentAdapter;
import com.example.projectone.adapter.PostAdapter;
import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private long mParam1;
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerView;

    public CommentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CommentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommentsFragment newInstance(long param1) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 =getArguments().getLong(ARG_PARAM1);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_comments, container, false);
        recyclerView=view.findViewById(R.id.comment_recycler);
        getComentarios();
        return view;
    }

    private void getComentarios(){

        Call<List<Comentario>> comentarioCall= ApiClient.getClientGson().create(ApiInterface.class).getCommentPost(mParam1);
        comentarioCall.enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                commentAdapter=new CommentAdapter((ArrayList<Comentario>) response.body(),getActivity());
                recyclerView.setAdapter(commentAdapter);

            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Log.i("c",String.valueOf(t));
            }
        });


    }
}