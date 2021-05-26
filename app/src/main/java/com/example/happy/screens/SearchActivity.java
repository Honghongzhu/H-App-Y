package com.example.happy.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.adapters.MovieAdapter;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.SavedMovies;
import com.example.happy.queries.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinkedList<MovieInfo> foundMovies;
    private LinkedList<SavedMovies> foundSavedMovies;
    private Button searchButton;
    private EditText searchText;
    private String search;
    private MovieAdapter mAdapter;
    private List<MovieInfo> allMovies;
    private List<SavedMovies> allSavedMovies;
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

        // After clicking on search button, it will go through all movies and compare the user input with the primary_title
        searchButton.setOnClickListener(v -> {

            Utils.hideKeyboard(this);

            search = searchText.getText().toString();

            if(search != null && !search.trim().isEmpty()){
                try {
                    allMovies = Utils.executeQuery(
                            MovieInfo.class,
                            SearchActivity.this,
                            "select",
                            "movie_id, primary_title, start_year, poster_url",
                            "movie_info",
                            "where",
                            String.format("primary_title like '%%%s%%'",
                                    search)
                    );

                    allSavedMovies = Utils.executeQuery(
                            SavedMovies.class,
                            SearchActivity.this,
                            "select",
                            "*",
                            "saved_movies",
                            "join",
                            String.format("movie_info on saved_movies.movie_id = movie_info.movie_id " +
                                    "where saved_movies.user_id=%s and movie_info.primary_title like '%%%s%%'",
                                    currentUserId, search)
                    );

                    // if no result
                    if (allMovies.toString().equals("[]")){
                        Toast toast = Toast.makeText(this, "No movie found with title: \"\"" + search, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else{
                        foundMovies = new LinkedList<>(allMovies);
                        foundSavedMovies = new LinkedList<>(allSavedMovies);
                    }

                } catch (ExecutionException e) {
                    Toast.makeText(SearchActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    Toast.makeText(SearchActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast toast = Toast.makeText(this, "Please write something before searching" + search, Toast.LENGTH_SHORT);
                foundMovies = new LinkedList<>();
                foundSavedMovies = new LinkedList<>();
            }

            mAdapter = new MovieAdapter(v.getContext(), foundMovies, foundSavedMovies, currentUserId);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        });
    }
}
