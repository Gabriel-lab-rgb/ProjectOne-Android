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

import com.bumptech.glide.Glide;
import com.example.projectone.entity.Sala;
import com.example.projectone.R;
import com.example.projectone.entity.Usuario;
import com.example.projectone.network.ApiClient;

import java.util.ArrayList;

public class SalaAdapter extends RecyclerView.Adapter<SalaAdapter.ViewHolder> {

    private ArrayList<Sala> salas;

    private SalaClickListener salaClickListener;
    private String username;

    private Context context;


    public SalaAdapter(ArrayList<Sala> salas, String username, SalaClickListener salaClickListener, Context context) {
        this.salas = salas;
        this.username =username;
        this.salaClickListener=salaClickListener;
        this.context=context;


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

        Usuario otroUsuario = obtenerOtroUsuario(salas.get(position));
        String imageUri = ApiClient.API_BASE_URL + "img/" + otroUsuario.getImage();
        if(imageUri!=null){
            Glide.with(context)
                    .load(imageUri)
                    .into(holder.image);
        }
        holder.username.setText(otroUsuario.getUsername());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salaClickListener.onItemClick(salas.get(holder.getAdapterPosition()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return salas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout constraintLayout;
        private ImageView image;
        private TextView username;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.item_c);
            image=itemView.findViewById(R.id.chats_image);
            username=itemView.findViewById(R.id.chats_username);

        }
    }

    public interface SalaClickListener{
        public void onItemClick(Sala sala);

    }

    public Usuario obtenerOtroUsuario(Sala sala) {
            for (Usuario usuario : sala.getUsuarios()) {
                if (!usuario.getUsername().equals(username)) {
                    return usuario; // Retorna el otro usuario
                }
            }

        return null;
    }
}
