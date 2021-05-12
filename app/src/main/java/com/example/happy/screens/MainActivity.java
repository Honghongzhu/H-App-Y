package com.example.happy.screens;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.data.Movie;
import com.example.happy.queries.ConnectionInterface;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.QueryObject;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView result;

    public void getQuery(QueryObject queryObject) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://elara.science.ru.nl/MeaningfulMovies1.7/")
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectionInterface connectionInterface = retrofit.create(ConnectionInterface.class);

        Call<List<MovieInfo>> call = connectionInterface.getMovieInfo(queryObject);

        call.enqueue(new Callback<List<MovieInfo>>() {
            @Override
            public void onResponse(Call<List<MovieInfo>> call, Response<List<MovieInfo>> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
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

                Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<MovieInfo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewResult = findViewById(R.id.result);

        QueryObject query = new QueryObject("movie_id, primary_title, original_title, start_year, runtime, genres, poster_url", "movie_info");

        getQuery(query);

    }

    public void launchRecommendationCSActivity(View view) {
        Intent intent = new Intent(this, RecommendationCSActivity.class);
        startActivity(intent);
    }

    public void launchTop12Activity(View view) {
        Intent intent = new Intent(this, Top12Activity.class);
        startActivity(intent);
    }

    public void launchHistoryActivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void launchSavedActivity(View view) {
        Intent intent = new Intent(this, SavedActivity.class);
        startActivity(intent);
    }

    public void launchSearchActivity(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

}
