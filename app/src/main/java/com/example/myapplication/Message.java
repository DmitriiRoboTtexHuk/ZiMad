package com.example.myapplication;

import android.icu.util.ULocale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("title")
    @Expose
    private String title;


}
