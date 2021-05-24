package com.example.happy.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.adapters.MovieAdapter;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinkedList<MovieInfo> foundMovies;
    private Button searchButton;
    private EditText searchText;
    private String search;
    private MovieAdapter mAdapter;
    private List<MovieInfo> allMovies;
    private int currentUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("CURRENT_USER_ID", -1);
        }

        mRecyclerView = findViewById(R.id.rv_search);
        searchText = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);

        // Query all movies
        try {
            allMovies = Utils.executeQuery(
                    MovieInfo.class,
                    SearchActivity.this,
                    "select",
                    "*",
                    "movie_info",
                    "",
                    ""
            );
        } catch (ExecutionException e) {
            Toast.makeText(SearchActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(SearchActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        // After clicking on search button, it will go through all movies and compare the user input with the primary_title
        searchButton.setOnClickListener(v -> {
            foundMovies = new LinkedList<>();
            search = searchText.getText().toString();
            for (MovieInfo movie : allMovies){
                if(movie.getPrimaryTitle().toLowerCase().contains(search.toLowerCase())){
                    foundMovies.add(movie);
                }
            }
            mAdapter = new MovieAdapter(v.getContext(), foundMovies, currentUserId);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        });
    }

//    public void launchRateActivity(View view) {
//        Intent intent = new Intent(this, RateActivity.class);
//        intent.putExtra("CURRENT_USER_ID", currentUserId);
//        startActivity(intent);
//    }
}
