package com.example.happy.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.happy.R;
import com.example.happy.adapters.MovieAdapter;
import com.example.happy.data.Movie;
import com.example.happy.data.MovieDatabase;

import java.util.LinkedList;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinkedList<Movie> allMovies;
    private LinkedList<Movie> foundMovies;
    private Button searchButton;
    private EditText searchText;
    private String search;
    private MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        allMovies = MovieDatabase.getAllMovies();

        mRecyclerView = findViewById(R.id.rv_search);
        searchText = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foundMovies = new LinkedList<>();
                search = searchText.getText().toString();
                for(Movie movie : allMovies){
                    if(movie.getName().toLowerCase().contains(search.toLowerCase())){
                        foundMovies.add(movie);
                    }
                }
                mAdapter = new MovieAdapter(v.getContext(), foundMovies);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
            }
        });
    }

    public void launchRateActivity(View view) {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
    }
}