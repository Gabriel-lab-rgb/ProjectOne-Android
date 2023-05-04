package com.example.projectone.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Post {

    @SerializedName("id")
    private long id;

    @SerializedName("usuario")
    private Usuario usuario;

    @SerializedName("comunidad")
    private Comunidad comunidad;

    @SerializedName("fecha")
    private Date fecha;

    @SerializedName("texto")
    private String texto;

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

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
