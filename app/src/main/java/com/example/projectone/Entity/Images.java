package com.example.projectone.Entity;

import com.google.gson.annotations.SerializedName;

public class Images {
    @SerializedName("original")
    public Original original;

    public Original getOriginal() {
        return original;
    }
}
