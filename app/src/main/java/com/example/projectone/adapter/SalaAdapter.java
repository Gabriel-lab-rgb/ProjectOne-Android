package com.example.projectone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectone.Entity.ChatMensaje;
import com.example.projectone.Entity.Post;
import com.example.projectone.Entity.Sala;
import com.example.projectone.Entity.UsuarioSummary;
import com.example.projectone.R;

import java.util.ArrayList;

public class SalaAdapter extends RecyclerView.Adapter<SalaAdapter.ViewHolder> {

    private ArrayList<Sala> salas;

    private SalaClickListener salaClickListener;
    private String username;


    public SalaAdapter(ArrayList<Sala> salas, String username, SalaClickListener salaClickListener) {
        this.salas = salas;
        this.username =username;
        this.salaClickListener=salaClickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /*Glide.with(context).load(posts.get(position).getImage()).into(holder.image);*/
        holder.username.setText(salas.get(position).getUsuario().getUsername());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salaClickListener.onItemClick(salas.get(holder.getAdapterPosition()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout constraintLayout;
        private ImageView image;
        private TextView username;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.item_u);
            image=itemView.findViewById(R.id.seach_image);
            username=itemView.findViewById(R.id.seach_username);

        }
    }

    public interface SalaClickListener{
        public void onItemClick(Sala sala);

    }
}
