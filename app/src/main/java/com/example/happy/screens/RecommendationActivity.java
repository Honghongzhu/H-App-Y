package com.example.happy.screens;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.adapters.MovieAdapter;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.MovieRatings;
import com.example.happy.queries.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class RecommendationActivity extends AppCompatActivity {

    private List<MovieRatings> resultMovieRatings;
    private List<MovieInfo> allMovieInfo;
    private int currentUserId = -1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("CURRENT_USER_ID", -1);
        }

        ArrayList<String> chosenCS = (ArrayList<String>) getIntent().getSerializableExtra("chosenCS");
        String listCS = String.join(", ", chosenCS);
        String listSumCS = String.join(" + ", chosenCS);

        // Query all movieRatings with the requested CS of the user
        try {
            resultMovieRatings = Utils.executeQuery(
                    MovieRatings.class,
                    RecommendationActivity.this,
                    "select",
                    String.format("movie_id, average_enjoyment, average_meaning, %s, SUM(%s)", listCS, listSumCS),
                    "movie_ratings",
                    "GROUP BY",
                    String.format("movie_id HAVING SUM(%s) >0 ORDER BY SUM(%s) DESC, average_meaning DESC, average_enjoyment DESC", listSumCS, listSumCS)
            );
        } catch (ExecutionException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        // Get the first 12 movies
        List<MovieRatings> top12Movies = resultMovieRatings.stream().limit(12).collect(Collectors.toList());
        // Get movieId
        ArrayList<String> top12MovieIds = new ArrayList<>();
        for(MovieRatings mr : top12Movies) {
            top12MovieIds.add(mr.getMovieId());
        }

        StringBuilder movieIDs = new StringBuilder();
        for(String movieId : top12MovieIds) {
            movieIDs.append(String.format("'%s', ", movieId));
        }
        movieIDs.delete(movieIDs.length()-2, movieIDs.length());
        // Query all movies with the corresponding movieID
        try {
            allMovieInfo = Utils.executeQuery(
                    MovieInfo.class,
                    RecommendationActivity.this,
                    "select",
                    "movie_id, primary_title, start_year, poster_url",
                    "movie_info",
                    "WHERE movie_id in",
                    String.format("(%s) ORDER BY FIELD(movie_id, %s)", movieIDs.toString(), movieIDs.toString())
            );
        } catch (ExecutionException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        LinkedList<MovieInfo> moviesSaved = new LinkedList<>(allMovieInfo);

        RecyclerView recyclerView = findViewById(R.id.rv_recommendation);
        MovieAdapter adapter = new MovieAdapter(this, moviesSaved, currentUserId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void launchRateActivity(View view) {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
    }
}
