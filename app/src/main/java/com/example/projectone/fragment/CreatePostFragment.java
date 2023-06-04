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
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.projectone.MainActivity;
import com.example.projectone.R;
import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;
import com.example.projectone.utils.MediaUtils;
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


public class CreatePostFragment extends Fragment implements  NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView navigationView;
    private BottomSheetDialog bottomSheetDialog;

    private Button publicar;
    private EditText text;
    private View bottomSheetView;

    private String currentUsuario;
    public static final String USERNAME_OR_EMAIL="user_key";

    SharedPreferences sharedPreferences;

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
                    if(tipo=="Images"){
                        imagen.setImageURI(uri);
                        imagen.setVisibility(View.VISIBLE);
                        video.setVisibility(View.GONE);
                    }else{
                        video.setVideoURI(uri);
                        imagen.setVisibility(View.GONE);
                        video.setVisibility(View.VISIBLE);
                    }
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
        View view= inflater.inflate(R.layout.fragment_create_post, container, false);

        currentUsuario= SharedPreferencesUtils.getString(getActivity(), USERNAME_OR_EMAIL, null);
        navigationView=view.findViewById(R.id.bottomNavigationView);
        navigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
        bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.layout_bottom_sheet_gif, view.findViewById(R.id.bottomSheetContainer));
        bottomSheetDialog.setContentView(bottomSheetView);
        publicar=view.findViewById(R.id.button_publicar);
        text=view.findViewById(R.id.editText_text);
        imagen =view.findViewById(R.id.imagePreview);
        video=view.findViewById(R.id.videoPreview);
        tipo="Text";

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(text.getText().toString().isEmpty()){
                    createPost();
                }
            }
        });
        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.image:
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
                tipo="Images";
                break;
            case R.id.video:
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                        .build());
                tipo="Video";
                break;
        }

        return false;
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




}