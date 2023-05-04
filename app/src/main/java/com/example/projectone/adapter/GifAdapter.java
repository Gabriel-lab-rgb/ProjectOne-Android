package com.example.projectone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.projectone.Entity.Gif;
import com.example.projectone.R;


import java.util.List;



public class GifAdapter extends RecyclerView.Adapter<GifAdapter.ViewHolder> {
 private Gif gifs;
 private Context context;

    public GifAdapter(Gif gifs, Context context) {
        this.gifs = gifs;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gif,parent,false);
        return  null;//new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(gifs.getDataList().get(position).getImages().getOriginal().getUrl()).into(holder.imageGif);
    }

    @Override
    public int getItemCount() {
        return gifs.getDataList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageGif;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageGif=itemView.findViewById(R.id.imageView);
        }
    }
}
