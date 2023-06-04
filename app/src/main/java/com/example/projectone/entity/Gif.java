package com.example.projectone.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Gif {
        @SerializedName("data")
        private List<Data> dataList;

    public List<Data> getDataList() {
        return dataList;
    }

    public class Data{

            @SerializedName("url")
            private String url;

        @SerializedName("images")
        private Images images;


        public Images getImages() {
            return images;
        }
    }



}

