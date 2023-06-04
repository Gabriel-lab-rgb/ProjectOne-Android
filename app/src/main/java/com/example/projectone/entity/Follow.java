package com.example.projectone.entity;

import com.google.gson.annotations.SerializedName;

public class Follow {

    @SerializedName("id")
    private Long id;

    @SerializedName("follower")
    private Usuario follower;

    @SerializedName("followed")
    private Usuario followed;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getFollower() {
        return follower;
    }

    public void setFollower(Usuario follower) {
        this.follower = follower;
    }

    public Usuario getFollowed() {
        return followed;
    }

    public void setFollowed(Usuario followed) {
        this.followed = followed;
    }


    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", follower=" + follower +
                ", followed=" + followed +
                '}';
    }
}
