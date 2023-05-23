package com.example.projectone.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UsuarioSummary {

        @SerializedName("id")
        private long id;
        @SerializedName("email")
        private String email;
        @SerializedName("username")
        private String username;
        @SerializedName("image")
        private String image;

       public long getId() {
          return id;
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }

        public String getImage() {
            return image;
        }
    }

