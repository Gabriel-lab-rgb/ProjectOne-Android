package com.example.projectone.entity;

import com.google.gson.annotations.SerializedName;

public class Tipo {

    @SerializedName("id")
    private long id;
    @SerializedName("nombre")
    private String nombre;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
