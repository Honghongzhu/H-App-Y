package com.example.happy.queries;

import android.content.Context;
import android.widget.Toast;

import com.example.happy.screens.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class QueryObject {

    private String columns;
    private String table;
    private String whereStatement;
    private String condition;

    public QueryObject(String columns, String table, String whereStatement, String condition) {
        this.columns = columns;
        this.table = table;
        this.whereStatement = whereStatement;
        this.condition = condition;
    }

    public void getQuery(final Context screen) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://elara.science.ru.nl/MeaningfulMovies1.49/")
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectionInterface connectionInterface = retrofit.create(ConnectionInterface.class);
        Call<List<MovieInfo>> call = connectionInterface.getMovieInfo(this);

        call.enqueue(new Callback<List<MovieInfo>>() {
            @Override
            public void onResponse(Call<List<MovieInfo>> call, Response<List<MovieInfo>> response) {

                if (!response.isSuccessful()){
                    Toast.makeText( screen, "Code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                List<MovieInfo> results = response.body();

                String content = "";

                for (MovieInfo result : results) {

                    content += "ID: " + result.getMovieId() + "\n";
                    content += "primaryTitle: " + result.getPrimaryTitle()+ "\n";
                    content += "originalTitle: " + result.getOriginalTitle() + "\n";
                    content += "year: " + result.getStartYear() + "\n";
                    content += "runtime: " + result.getRuntime() + "\n";
                    content += "genres: " + result.getGenres() + "\n";
                    content += "posterUrl: " + result.getPosterUrl() + "\n\n";
                }

                Toast.makeText(screen, content, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<MovieInfo>> call, Throwable t) {
                Toast.makeText(screen, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
