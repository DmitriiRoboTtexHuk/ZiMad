package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class CountModel {
    @SerializedName("data")
    @Expose
    public List<Message> Animals;
}
