package com.example.happy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;

public class SavedActivity extends AppCompatActivity {
    private LinkedList<Movie> moviesSaved;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        moviesSaved = MovieDatabase.getSavedMovies();

        recyclerView = findViewById(R.id.rv_saved);
        adapter = new MovieAdapter(this, moviesSaved);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
