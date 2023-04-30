package com.example.projectone.Entity;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("post")
    private Set<Post> posts;

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
