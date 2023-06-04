package com.example.projectone.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectone.entity.Comentario;
import com.example.projectone.R;
import com.example.projectone.network.ApiClient;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private ArrayList<Comentario> comentarios;
    private Context context;

    private int lastPosition=-1;


    public CommentAdapter(ArrayList<Comentario> comentarios, Context context) {
        this.comentarios = comentarios;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String imageUri = ApiClient.API_BASE_URL + "img/" + comentarios.get(position).getUsuario().getImage();
        if(imageUri!=null){
            Glide.with(context)
                    .load(imageUri)
                    .into(holder.image);
        }
        holder.username.setText(comentarios.get(position).getUsuario().getUsername());
        holder.texto.setText(comentarios.get(position).getTexto());



    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }
    public int getLastPosition() {
        return getItemCount() - 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView username;
        private TextView texto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.comment_image_username);
            username=itemView.findViewById(R.id.comment_username);
            texto=itemView.findViewById(R.id.comment_text);

        }
    }

    public void addComentario(Comentario comentario) {
        comentarios.add(comentario);
    }
}
