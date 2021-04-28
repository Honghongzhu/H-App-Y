package com.example.happy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.LinkedList;

public class Top10Activity extends AppCompatActivity {
    private LinkedList<Movie> movieList;
    private RecyclerView recyclerView;
    private RankAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);

        movieList = MovieDatabase.getAllMovies();

        // Get a handle to the RecyclerView
        recyclerView = findViewById(R.id.rv_top12);
        // Create an adapter and supply the data to be displayed
        adapter = new RankAdapter(this, movieList);
        // Connect the adapter with the RecyclerView
        recyclerView.setAdapter(adapter);
        // Give the RecyclerView a default layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void launchRateActivity(View view) {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
    }
}
