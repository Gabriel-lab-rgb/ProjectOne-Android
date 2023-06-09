package com.example.projectone.fragment;


import android.content.SharedPreferences;

import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.projectone.MainActivity;
import com.example.projectone.R;
import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;
import com.example.projectone.utils.MediaUtils;
import com.example.projectone.utils.ProgressBarUtils;
import com.example.projectone.utils.SharedPreferencesUtils;
import com.example.projectone.utils.SnackbarUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreatePostFragment extends Fragment implements View.OnClickListener {

    private Button publicar;
    private EditText text;

    private ProgressBarUtils progressBarUtils;

    private CardView card;

    private String currentUsuario;
    public static final String USERNAME_OR_EMAIL="user_key";

    private ImageView quitar;

    private String tipo;
    private ImageView imagen;
    private VideoView video;

    private File file;

    private Uri u;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);
                    u=uri;

                    String mimeType = getContext().getContentResolver().getType(uri);

                    if (mimeType != null && mimeType.startsWith("image/")) {
                        imagen.setImageURI(uri);
                        imagen.setVisibility(View.VISIBLE);
                        video.setVisibility(View.GONE);
                        tipo="Images";
                    } else if (mimeType != null && mimeType.startsWith("video/")) {
                        video.setVideoURI(uri);
                        imagen.setVisibility(View.GONE);
                        video.setVisibility(View.VISIBLE);
                        tipo="Video";
                    }
                    quitar.setVisibility(View.VISIBLE);
                    file = new File(MediaUtils.getRealPathFromURI(getActivity(), uri));

                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);


        currentUsuario = SharedPreferencesUtils.getString(getActivity(), USERNAME_OR_EMAIL, null);
        publicar = view.findViewById(R.id.button_publicar);
        card = view.findViewById(R.id.cardMedia);
        quitar = view.findViewById(R.id.quitar);
        card.setOnClickListener(this);
        quitar.setOnClickListener(this);
        publicar.setOnClickListener(this);

        progressBarUtils = new ProgressBarUtils(view);


        text = view.findViewById(R.id.editText_text);
        imagen = view.findViewById(R.id.imagePreview);
        video = view.findViewById(R.id.videoPreview);
        tipo = "Text";

        return view;

    }



    private void createPost(){


        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), currentUsuario);
        RequestBody contenido = RequestBody.create(MediaType.parse("text/plain"), text.getText().toString());
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), tipo);

        RequestBody requestFile = RequestBody.create(MediaType.parse(MediaUtils.getMediaTypeFromUri(getActivity(),u)), file);
        MultipartBody.Part mediaPart = MultipartBody.Part.createFormData("media", file.getName(), requestFile);

        Call<String> create= ApiClient.getClientString().create(ApiInterface.class).CreatePost(username,type,contenido,mediaPart);
        create.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                progressBarUtils.showProgressBar();
                if(response.isSuccessful()){
                    Log.i("c","post insertado correctamente");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                SnackbarUtils.showLongSnackbar(getActivity().findViewById(android.R.id.content), "Se ha producido un error de conexión", R.color.error);
                Log.i("c",t.getMessage());

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardMedia:
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                        .build());
                break;
            case R.id.quitar:
                imagen.setImageDrawable(null);
                video.setVideoURI(null);
                quitar.setVisibility(View.GONE);
                tipo="text";
                u=null;
                break;

            case R.id.button_publicar:
                if(!text.getText().toString().isEmpty() && u!=null){
                    createPost();
                }else {
                    Toast.makeText(getContext(), "No has subido nada", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}