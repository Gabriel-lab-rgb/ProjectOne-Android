package com.example.projectone.network;

import com.example.projectone.Entity.Usuario;

public interface UserInfoCallback {

    void onUserInfoReceived(Usuario user);
    void onUserInfoError(Throwable t);
}
