package com.example.projectone.entity;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.List;

public class Sala {

    @SerializedName("id")
    private long id;

    @SerializedName("fecha")
    private String fecha;
    @SerializedName("usuarios")
    private List<Usuario>usuarios;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
