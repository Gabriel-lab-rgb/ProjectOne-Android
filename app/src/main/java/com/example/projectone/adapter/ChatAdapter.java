package com.example.projectone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectone.Entity.ChatMensaje;
import com.example.projectone.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private ArrayList<ChatMensaje> mensajes;
    private String username;


    public ChatAdapter(ArrayList<ChatMensaje> mensajes, String username) {
        this.mensajes = mensajes;
        this.username =username;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChatMensaje chatMessage = mensajes.get(position);

        if (chatMessage.getUsuario().getUsername().equals(username)) {
            // Mensaje del usuario actual
            holder.layoutCurrentUser.setVisibility(View.VISIBLE);
            holder.layoutOtherUser.setVisibility(View.GONE);
            holder.textMessageCurrentUser.setText(chatMessage.getMensaje());
        } else {
            // Mensaje del otro usuario
            holder.layoutCurrentUser.setVisibility(View.GONE);
            holder.layoutOtherUser.setVisibility(View.VISIBLE);
            holder.textMessageOtherUser.setText(chatMessage.getMensaje());
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout layoutCurrentUser;
        public ConstraintLayout layoutOtherUser;
        public TextView textMessageCurrentUser;
        public TextView textMessageOtherUser;

        public ViewHolder(View itemView) {
            super(itemView);

            layoutCurrentUser = itemView.findViewById(R.id.layoutCurrentUser);
            layoutOtherUser = itemView.findViewById(R.id.layoutOtherUser);
            textMessageCurrentUser = itemView.findViewById(R.id.textMessageCurrentUser);
            textMessageOtherUser = itemView.findViewById(R.id.textMessageOtherUser);
        }
    }
}
