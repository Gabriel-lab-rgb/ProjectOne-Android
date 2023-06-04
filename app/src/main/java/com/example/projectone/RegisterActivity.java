package com.example.projectone;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectone.utils.SharedPreferencesUtils;
import com.example.projectone.utils.SnackbarUtils;
import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editUsername;
    private EditText editPassword;
    private EditText editEmail;
    private ImageView avatar;
    private File file;
    private Button btn_register;

    private TextView login;

    public static final String USERNAME_OR_EMAIL="user_key";
    public static final String PASSWORD="password_key";
    String usernameEmail,password;
    Bitmap bitmap;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);
                    avatar.setImageURI(uri);
                    file = new File(getRealPathFromURI(this,uri));

                    try {
                        bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        Log.d("c",bitmap.toString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Componentes del Activity
        editUsername=this.findViewById(R.id.register_username);
        editPassword=this.findViewById(R.id.register_password);
        editEmail=this.findViewById(R.id.register_email);
        avatar=this.findViewById(R.id.register_avatar);
        btn_register=this.findViewById(R.id.btn_register);
        /*login=this.findViewById(R.id.textLogin);*/


        usernameEmail= SharedPreferencesUtils.getString(this, USERNAME_OR_EMAIL, null);
        password=SharedPreferencesUtils.getString(this, PASSWORD, null);

        //Evento clickListener
        avatar.setOnClickListener(this);
        btn_register.setOnClickListener(this);
      /*  login.setOnClickListener(this);*/

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void register(){
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), editEmail.getText().toString());
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), editUsername.getText().toString());
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), editPassword.getText().toString());

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", file.getName(), requestFile);

        Call<String> create= ApiClient.getClientString().create(ApiInterface.class).userRegister(username,email,password,imagenPart);
        create.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    Log.i("c","usuario insertado correctamente");
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("c",t.getMessage());
                SnackbarUtils.showLongSnackbar(findViewById(android.R.id.content), "Se ha producido un error de conexión", R.color.error);
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_register) {
            if(editUsername.getText().toString().isEmpty() || editPassword.getText().toString().isEmpty() || editEmail.getText().toString().isEmpty()){
                SnackbarUtils.showLongSnackbar(findViewById(android.R.id.content), "Hay campos vacios", R.color.warning);
            }else{
                if(file!=null){
                    register();
                }else{
                    SnackbarUtils.showLongSnackbar(findViewById(android.R.id.content), "Debes añadir una imagen de perfil", R.color.error);
                }
            }
        }else if(v.getId() == R.id.register_avatar){
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());

        } /*else if (v.getId() == R.id.textLogin) {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }*/
    }


    public String getRealPathFromURI(Context context, Uri uri) {
        String filePath = "";
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath;
    }
}