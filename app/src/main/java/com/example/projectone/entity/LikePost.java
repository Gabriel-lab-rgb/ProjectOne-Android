package com.example.projectone.entity;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class LikePost {

    @SerializedName("id")
    private long id;
    @SerializedName("usuario")
    private Usuario usuario;
    @SerializedName("post")
    private Post post;
    @SerializedName("fecha")
    private Date fecha;

    public LikePost(Usuario usuario) {
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "LikePost{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", post=" + post +
                ", fecha=" + fecha +
                '}';
    }
}
