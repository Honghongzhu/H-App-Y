package com.example.happy.queries;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface ConnectionInterface {

    @POST("query")
    Call<List<MovieInfo>> getMovieInfo(@Body QueryObject queryObject);
}
