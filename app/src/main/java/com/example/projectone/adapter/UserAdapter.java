package com.example.projectone.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectone.entity.UsuarioSummary;
import com.example.projectone.R;
import com.example.projectone.network.ApiClient;

import java.io.IOException;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{


    private ArrayList<UsuarioSummary> usuarios;
    private Context context;
    private PostAdapter.ItemClickListener clickListener;


    public UserAdapter(ArrayList<UsuarioSummary> usuarios, Context context, PostAdapter.ItemClickListener clickListener) {
        this.usuarios = usuarios;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String imageUri = ApiClient.API_BASE_URL + "img/" + usuarios.get(position).getImage();

if(imageUri!=null){
    Glide.with(context)
            .load(imageUri)
            .into(holder.image);

}

        holder.username.setText(usuarios.get(position).getUsername());
        /*holder.email.setText(usuarios.get(position).getEmail());*/

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onUserClick(usuarios.get(holder.getAdapterPosition()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout constraintLayout;
        private ImageView image;
        private TextView username;
        private TextView email;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.item_u);
            image=itemView.findViewById(R.id.seach_image);
            username=itemView.findViewById(R.id.seach_username);
            /*email=itemView.findViewById(R.id.seach_email);*/

        }
    }


}
