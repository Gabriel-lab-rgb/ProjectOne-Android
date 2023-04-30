package com.example.projectone.Form;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Body;
import retrofit2.http.Field;


public class LoginResponse {
    @SerializedName("usernameOrEmail")
    private String usernameOrEmail;
    @SerializedName("password")
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
