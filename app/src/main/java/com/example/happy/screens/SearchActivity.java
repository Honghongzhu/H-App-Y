package com.example.happy.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.adapters.MovieAdapter;
import com.example.happy.data.Movie;
import com.example.happy.data.MovieDatabase;
import com.example.happy.queries.Users;
import com.example.happy.queries.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

        try {
            List<Users> result = Utils.executeQuery(
                    Users.class,
                    SearchActivity.this,
                    "select",
                    "*",
                    "users",
                    "where",
                    "user_id=0"
            );

            if(result.toString() != "[]"){
                Toast.makeText(SearchActivity.this, result.get(0).getAndroidId(), Toast.LENGTH_LONG).show();
            }
        } catch (ExecutionException e) {
            Toast.makeText(SearchActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(SearchActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

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
