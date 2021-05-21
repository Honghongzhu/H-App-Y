package com.example.happy.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.adapters.RankAdapter;
import com.example.happy.data.Movie;
import com.example.happy.data.MovieDatabase;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.NoResult;
import com.example.happy.queries.UserRatings;
import com.example.happy.queries.Users;
import com.example.happy.queries.Utils;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Top12Activity extends AppCompatActivity {
    private LinkedList<Movie> movieList;
    private RecyclerView recyclerView;
    private RankAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top12);

        // Retrieve the current user of the app
        int currentUserId = -1;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("CURRENT_USER_ID", -1);
        }

        try {
            // Get the movies rated by the user
            List<UserRatings> currentUserRatings = Utils.executeQuery(
                    UserRatings.class,
                    Top12Activity.this,
                    "select",
                    "*",
                    "user_ratings",
                    "where",
                    String.format("user_id=%s", currentUserId)
            );

            // Get the info of every movie
            List<MovieInfo> movieInfoTable = Utils.executeQuery(
                    MovieInfo.class,
                    Top12Activity.this,
                    "select",
                    "*",
                    "movie_info",
                    "",
                    ""
            );

            String userRatingsJson = new Gson().toJson(currentUserRatings);
            String movieInfoJson = new Gson().toJson(movieInfoTable);

            // Now we call the python script that will return a string with ordered movie ids


        } catch (ExecutionException e) {
            Toast.makeText(Top12Activity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(Top12Activity.this, e.toString(), Toast.LENGTH_LONG).show();
        }




        /*
        movieList = MovieDatabase.getAllMovies();
        // Get a handle to the RecyclerView
        recyclerView = findViewById(R.id.rv_top12);
        // Create an adapter and supply the data to be displayed
        adapter = new RankAdapter(this, movieList);
        // Connect the adapter with the RecyclerView
        recyclerView.setAdapter(adapter);
        // Give the RecyclerView a default layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/
    }

    public void launchRateActivity(View view) {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
    }
}
