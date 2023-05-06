package com.example.projectone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.projectone.Entity.Post;
import com.example.projectone.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    private Context context;
    private ItemClickListener clickListener;

    public PostAdapter(ArrayList<Post> posts, Context context,ItemClickListener clickListener) {
        this.posts = posts;
        this.context = context;
        this.clickListener=clickListener;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {

        /*Glide.with(context).load(posts.get(position).getUsuario().getImage()).into(holder.image);*/
        holder.username.setText(posts.get(position).getUsuario().getUsername());
        holder.texto.setText(posts.get(position).getTexto());
        holder.fecha.setText(posts.get(position).getFecha().toString());
        holder.comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(posts.get(holder.getAdapterPosition()));

            }
        });
        /*
        holder.comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(posts.get(holder.getAdapterPosition()));

            }
        });*/


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView username;
        private TextView fecha;
        private TextView texto;

        private TextView like;

        private TextView comentarios;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.imageProfile);
            username=itemView.findViewById(R.id.post_username);
            fecha=itemView.findViewById(R.id.post_date);
            texto=itemView.findViewById(R.id.post_text);
            like=itemView.findViewById(R.id.post_like);
            comentarios=itemView.findViewById(R.id.post_comentarios);
        }
    }

    public interface ItemClickListener{
        public void onItemClick(Post post);
    }
}
