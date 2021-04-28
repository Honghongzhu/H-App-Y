package com.example.happy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
        searchButton = findViewById(R.id.searchButton);
        searchText = findViewById(R.id.searchBar);

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
}
