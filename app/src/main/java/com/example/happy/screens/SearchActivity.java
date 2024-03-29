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
                            "SELECT",
                            "movie_id, primary_title, start_year, poster_url",
                            "movie_info",
                            "WHERE",
                            String.format("primary_title LIKE '%%%s%%' ORDER BY start_year DESC",
                                    search)
                    );

                    allSavedMovies = Utils.executeQuery(
                            SavedMovies.class,
                            SearchActivity.this,
                            "SELECT",
                            "*",
                            "saved_movies",
                            "JOIN",
                            String.format("movie_info ON saved_movies.movie_id = movie_info.movie_id " +
                                    "WHERE saved_movies.user_id=%s AND movie_info.primary_title LIKE '%%%s%%'" +
                                    "ORDER BY movie_info.start_year DESC",
                                    currentUserId, search)
                    );

                    // if no result
                    if (allMovies.toString().equals("[]")){
                        Toast emptyToast = Toast.makeText(SearchActivity.this, "No movie found with title: \"" + search + "\"", Toast.LENGTH_SHORT);
                        emptyToast.setGravity(Gravity.CENTER, 0, 0);
                        emptyToast.show();
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
                Toast noSearchToast = Toast.makeText(SearchActivity.this, "Please write something before searching", Toast.LENGTH_SHORT);
                noSearchToast.setGravity(Gravity.CENTER, 0, 0);
                noSearchToast.show();
                foundMovies = new LinkedList<>();
                foundSavedMovies = new LinkedList<>();
            }

            mAdapter = new MovieAdapter(v.getContext(), foundMovies, foundSavedMovies, currentUserId);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        });
    }
}
