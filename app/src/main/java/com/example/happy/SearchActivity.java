package com.example.happy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.LinkedList;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinkedList<Movie> allMovies;
    private LinkedList<Movie> foundMovies = new LinkedList<>();
    private Button searchButton;
    private EditText searchText;
    private String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.rv_search);

        allMovies = MovieDatabase.getAllMovies();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = searchText.getText().toString();
                for (Movie movie : allMovies){
                    if(movie.getName().toLowerCase().contains(search.toLowerCase())){
                        foundMovies.add(movie);
                    }
                }
                MovieAdapter adapter = new MovieAdapter(v.getContext(), foundMovies);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
            }
        });

        //TODO: remove old searches


    }
}
