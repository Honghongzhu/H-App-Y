package com.example.happy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.LinkedList;

public class RecommendationActivity extends AppCompatActivity {

    private LinkedList<Movie> moviesToAdapt;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        moviesToAdapt = MovieDatabase.getAllMovies();
        recyclerView = findViewById(R.id.rv_recommendation);
        adapter = new MovieAdapter(this, moviesToAdapt);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void launchRateActivity(View view) {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
    }
}
