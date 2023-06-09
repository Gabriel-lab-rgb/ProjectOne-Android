package com.example.projectone.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.projectone.entity.Comentario;
import com.example.projectone.MainActivity;
import com.example.projectone.R;
import com.example.projectone.adapter.CommentAdapter;
import com.example.projectone.entity.Post;
import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;
import com.example.projectone.utils.ProgressBarUtils;
import com.example.projectone.utils.SnackbarUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    private static final String ARG_PARAM1 = "post";

    private static final String ARG_PARAM2 = "currentUsuario";

    // TODO: Rename and change types of parameters
    private Post post;
    private String currentUsuario;
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerView;

    private EditText comentario;

    private TextView comentarioLayout;

    private ProgressBarUtils progressBarUtils;



    /*Variable del post*/
    private TextView postUsuario;
    private ImageView postImagenUsuario;
    private VideoView postVideo;
    private ImageView postImagen;
    private TextView postContenido;
    private TextView nlikes;

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
    public static CommentsFragment newInstance(Post param1, String param2) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            post = (Post) getArguments().getSerializable(ARG_PARAM1);
            currentUsuario =getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.navigationView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.navigationView.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_comments, container, false);
        comentarioLayout=view.findViewById(R.id.comment_text);
        recyclerView=view.findViewById(R.id.comment_recycler);
        comentario=view.findViewById(R.id.comment_edit);
        progressBarUtils = new ProgressBarUtils(view);

        //Variables del post
        postUsuario=view.findViewById(R.id.post_username);
        postVideo=view.findViewById(R.id.relativeVideo);
        postImagenUsuario=view.findViewById(R.id.post_avatar);
        postImagen=view.findViewById(R.id.imageView8);
        nlikes=view.findViewById(R.id.post_number_likes);



        postUsuario.setText(post.getUsuario().getUsername());
        if(post.getTexto()!=null){
            postContenido=view.findViewById(R.id.post_text);
            postContenido.setText(post.getTexto());

        }

        if(post.getTipo().getNombre().equals("Video")){
            String videoPostUri =ApiClient.API_BASE_URL + "img/" + post.getVideo();
            postVideo.setVideoURI(Uri.parse(videoPostUri));
            MediaController mediaController = new MediaController(getContext());
            mediaController.setAnchorView(postVideo);
            postVideo.setMediaController(mediaController);

        }else{
            postVideo.setVisibility(View.GONE);

        }

        nlikes.setText(String.valueOf(post.getLikePosts().size()));
        String imageUri = ApiClient.API_BASE_URL + "img/" + post.getUsuario().getImage();
        if(imageUri!=null){
            Glide.with(getContext())
                    .load(imageUri)
                    .into(postImagenUsuario);
        }


        comentario.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    crearComentario(textView.getText().toString());
                    return true;
                }
                return false;
            }
        });
        getComentarios();
        return view;
    }

    private void getComentarios(){

        Call<List<Comentario>> comentarioCall= ApiClient.getClientGson().create(ApiInterface.class).getCommentPost(post.getId());
        comentarioCall.enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {

                if(response.isSuccessful()){
                    if(response.body().size()>0){
                        commentAdapter=new CommentAdapter((ArrayList<Comentario>) response.body(),getActivity());
                        recyclerView.setAdapter(commentAdapter);
                    }else{
                        commentAdapter=new CommentAdapter(new ArrayList<Comentario>(),getActivity());
                        recyclerView.setAdapter(commentAdapter);
                        comentarioLayout.setVisibility(View.VISIBLE);
                    }


                }else{
                    Log.i("c", String.valueOf(response.code()));
                }
                progressBarUtils.hideProgressBar();


            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                SnackbarUtils.showLongSnackbar(getActivity().findViewById(android.R.id.content), "Se ha producido un error de conexión", R.color.error);
                progressBarUtils.hideProgressBar();
                Log.i("c",String.valueOf(t));
            }
        });


    }

    private void crearComentario(String comentario){
        progressBarUtils.showProgressBar();
        Call<Comentario> comentarioCall= ApiClient.getClientGson().create(ApiInterface.class).createComment(post.getId(),currentUsuario,comentario);
        comentarioCall.enqueue(new Callback<Comentario>() {
            @Override
            public void onResponse(Call<Comentario> call, Response<Comentario> response) {

                if(response.isSuccessful()) {
                    Toast.makeText(getContext(), "Comentario añadido", Toast.LENGTH_SHORT).show();
                    commentAdapter.addComentario(response.body());
                    commentAdapter.notifyDataSetChanged();

                }else{
                    Log.i("c", String.valueOf(response.code()));
                }
                progressBarUtils.hideProgressBar();

            }

            @Override
            public void onFailure(Call<Comentario> call, Throwable t) {
                SnackbarUtils.showLongSnackbar(getActivity().findViewById(android.R.id.content), "Se ha producido un error de conexión", R.color.error);
                progressBarUtils.hideProgressBar();
                Log.i("c",String.valueOf(t));
            }
        });

    }
}