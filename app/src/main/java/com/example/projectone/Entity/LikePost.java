package com.example.projectone.Entity;

import java.sql.Date;
import java.util.Objects;

public class LikePost {

    private long id;

    private Usuario usuario;

    private Post post;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikePost likePost = (LikePost) o;
        return Objects.equals(usuario, likePost.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario);
    }
}
