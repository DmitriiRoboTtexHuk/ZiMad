package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MessagesApi {
    @GET("api.php")
    Call<CountModel> request(@Query("query") String description );
}
