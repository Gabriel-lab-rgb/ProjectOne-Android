package com.example.projectone.adapter;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectone.entity.LikePost;
import com.example.projectone.entity.Post;
import com.example.projectone.entity.Usuario;
import com.example.projectone.entity.UsuarioSummary;
import com.example.projectone.R;
import com.example.projectone.network.ApiClient;
import com.example.projectone.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    private Context context;
    private ItemClickListener clickListener;

    private String username;

    public PostAdapter(ArrayList<Post> posts,String username, Context context,ItemClickListener clickListener) {
        this.posts = posts;
        this.username =username;
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
        String imageUri = ApiClient.API_BASE_URL + "img/" + posts.get(position).getUsuario().getImage();


        if(posts.get(position).getTipo().getNombre().equals("Video")){
            String videoPostUri =ApiClient.API_BASE_URL + "img/" + posts.get(position).getVideo();
               holder.video.setVideoURI(Uri.parse(videoPostUri));
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(holder.video);
            holder.video.setMediaController(mediaController);
        }else {
            holder.video.setVisibility(View.GONE);
        }
        if(imageUri!=null){
            Glide.with(context)
                    .load(imageUri)
                    .into(holder.image);
        }
        holder.username.setText(posts.get(position).getUsuario().getUsername());
        holder.texto.setText(posts.get(position).getTexto());
        holder.nlikes.setText(String.valueOf(posts.get(position).getLikePosts().size()));
        /*holder.ncomments.setText(posts.get(position));*/
        /*holder.fecha.setText(posts.get(position).getFecha().toString());*/
        /*holder.like.setText(posts.get(position).getLikePosts().size());
        holder.comentarios.setText("0");*/

        Log.i("c",posts.get(position).getLikePosts().toString());
        holder.comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(posts.get(holder.getAdapterPosition()));

            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("c","hola");

                Post post=posts.get(holder.getAdapterPosition());
                boolean exists = false;
                LikePost likePostToRemove = null;
                for (LikePost likePost : post.getLikePosts()) {
                    if (likePost.getUsuario() != null && username.equals(likePost.getUsuario().getUsername())) {
                        exists = true;
                        likePostToRemove = likePost;

                        break;
                    }
                }

                if (exists) {
                    // El LikePost con el nombre de usuario específico existe en la lista
                    post.getLikePosts().remove(likePostToRemove);
                    deleteLike(post.getId());
                    holder.nlikes.setText(String.valueOf(posts.get(position).getLikePosts().size()-1));
                } else {
                    // El LikePost con el nombre de usuario específico no existe en la lista
                    LikePost likePostToAdd = new LikePost(new Usuario(username));
                    post.getLikePosts().add(likePostToAdd);
                    addLike(post.getId());
                    holder.nlikes.setText(String.valueOf(posts.get(position).getLikePosts().size()+1));
                }

                notifyItemChanged(holder.getAdapterPosition());
            }


        });


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
        private VideoView video;
        private ImageView like;
        private TextView nlikes;
        private TextView ncomments;

        private ImageView comentarios;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.post_avatar);
            username=itemView.findViewById(R.id.post_username);
            fecha=itemView.findViewById(R.id.post_date);
            texto=itemView.findViewById(R.id.post_text);
            like=itemView.findViewById(R.id.post_like);
            comentarios=itemView.findViewById(R.id.post_comentarios);
            video=itemView.findViewById(R.id.relativeVideo);
            nlikes=itemView.findViewById(R.id.post_number_likes);
            ncomments=itemView.findViewById(R.id.post_number_comments);
        }
    }

    public interface ItemClickListener{
        public void onItemClick(Post post);

        public void onUserClick(UsuarioSummary user);
    }

    public void addLike(long id){


        Call<String> create= ApiClient.getClientString().create(ApiInterface.class).addLike(id,username);
        create.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    Log.i("c","like insertado correctamente");
                }else{
                    Log.i("c", String.valueOf(response.code()));
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("c",t.getMessage());
            }
        });
    }

    public void deleteLike(long id){

        Call<String> delete= ApiClient.getClientString().create(ApiInterface.class).deleteLike(id,username);
        delete.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    Log.i("c","like eliminado correctamente");
                }else{
                    Log.i("c", String.valueOf(response.code()));
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("c",t.getMessage());
            }
        });
    }


}
