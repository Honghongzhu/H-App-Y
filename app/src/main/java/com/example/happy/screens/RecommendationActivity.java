package com.example.happy.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.adapters.MovieAdapter;
import com.example.happy.adapters.RankAdapter;
import com.example.happy.data.Movie;
import com.example.happy.data.MovieDatabase;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.MovieRatings;
import com.example.happy.queries.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecommendationActivity extends AppCompatActivity {

    //private LinkedList<MovieInfo> moviesToAdapt;
    private List<MovieInfo> allMovieInfo;
    private List<MovieRatings> allMovieRatings;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        ArrayList<String> chosenCS = (ArrayList<String>) getIntent().getSerializableExtra("chosenCS");
//        moviesToAdapt = MovieDatabase.getCreativityMovies();
//        recyclerView = findViewById(R.id.rv_recommendation);
//        adapter = new MovieAdapter(this, moviesToAdapt);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Query all movieInfo
        try {
            allMovieInfo = Utils.executeQuery(
                    MovieInfo.class,
                    RecommendationActivity.this,
                    "select",
                    "*",
                    "movie_info",
                    "",
                    ""
            );
        } catch (ExecutionException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        // Query all movieRatings
        try {
            allMovieRatings = Utils.executeQuery(
                    MovieRatings.class,
                    RecommendationActivity.this,
                    "select",
                    "*",
                    "movie_ratings",
                    "",
                    ""
            );
        } catch (ExecutionException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void launchRateActivity(View view) {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
    }
}
