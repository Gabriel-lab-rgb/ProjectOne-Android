package com.example.projectone.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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

import com.example.projectone.R;
import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreatePostFragment extends Fragment implements  NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView navigationView;
    private BottomSheetDialog bottomSheetDialog;

    private Button publicar;
    private EditText text;
    private View bottomSheetView;

    public static final String SHARED_PREFERENCES="shared_prefs";
    public static final String USERNAME_OR_EMAIL="user_key";

    SharedPreferences sharedPreferences;

    private String tipo;
    private ImageView image;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia =
            registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(2), uris -> {
                // Callback is invoked after the user selects media items or closes the
                // photo picker.
                if (!uris.isEmpty()) {
                    image.setImageURI(uris.get(0));
                    Log.d("PhotoPicker", "Number of items selected: " + uris.size());
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_create_post, container, false);

        sharedPreferences=getContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        navigationView=view.findViewById(R.id.bottomNavigationView);
        navigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
        bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.layout_bottom_sheet_gif, view.findViewById(R.id.bottomSheetContainer));
        bottomSheetDialog.setContentView(bottomSheetView);
        publicar=view.findViewById(R.id.button_publicar);
        text=view.findViewById(R.id.editText_text);
        image=view.findViewById(R.id.imageView4);

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

createPost();

            }
        });
        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.image:
                pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
                tipo="Images";
                break;
           /* case R.id.gif:
                bottomSheetDialog.show();
                tipo="Gif";
            break;*/

            case R.id.video:
                pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                        .build());
                tipo="Video";
                break;
        }

        return false;
    }

    private String convertirImgString(Bitmap bitmap){

        return null;
    }

    private void createPost(){

        Map<String,String> params = new HashMap<String, String>();
        params.put("username", sharedPreferences.getString(USERNAME_OR_EMAIL,null));
        params.put("comunidad", "");
        params.put("type",tipo);
        params.put("texto",text.getText().toString());
        if(tipo.equals("Gif")){
            params.put("gif", "");
        }else if(tipo.equals("Video")){
            params.put("video", "");

        }else if(tipo.equals("images")){

        }




        Call<String> create= ApiClient.getClientString().create(ApiInterface.class).CreatePost(params);
        create.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    Log.i("c","post insertado correctamente");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("c",t.getMessage());
            }
        });
    }


}