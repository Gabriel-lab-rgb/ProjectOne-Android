package com.example.projectone.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class Usuario {

    @SerializedName("id")
    private long id;
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;
    @SerializedName("image")
    private String image;
    @SerializedName("posts")
    private ArrayList<Post> posts;

    @SerializedName("seguidores")
    private ArrayList<Follow> seguidores;

    @SerializedName("siguiendo")
    private ArrayList<Follow> siguiendo;

    public Usuario(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Follow> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(ArrayList<Follow> seguidores) {
        this.seguidores = seguidores;
    }

    public ArrayList<Follow> getSiguiendo() {
        return siguiendo;
    }

    public void setSiguiendo(ArrayList<Follow> siguiendo) {
        this.siguiendo = siguiendo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", image='" + image + '\'' +
                ", posts=" + posts +
                ", seguidores=" + seguidores +
                ", siguiendo=" + siguiendo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return username.equals(usuario.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
