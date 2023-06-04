package com.example.projectone.network;

import com.example.projectone.entity.Usuario;

public interface UserInfoCallback {

    void onUserInfoReceived(Usuario user);
    void onUserInfoError(Throwable t);
}
