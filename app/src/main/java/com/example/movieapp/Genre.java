package com.example.movieapp;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

public class Genre {

    private Integer id;
    private String name;
    private Integer resId;

    public Genre(Integer id, String name, Integer resId){
        this.id = id;
        this.name = name;
        this.resId = resId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer id) {
        this.resId = id;
    }
}
