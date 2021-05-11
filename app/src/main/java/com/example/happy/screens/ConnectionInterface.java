package com.example.happy.screens;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ConnectionInterface {

    @GET("query")
    Call<List<Post>> getPosts();

}
