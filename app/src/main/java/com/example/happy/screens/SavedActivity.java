package com.example.happy.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.adapters.MovieAdapter;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.SavedMovies;
import com.example.happy.queries.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SavedActivity extends AppCompatActivity {
    private List<MovieInfo> allSaved;
    private List<SavedMovies> savedByUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        // Retrieve the current user of the app
        int currentUserId = -1;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("CURRENT_USER_ID", -1);
        }

        try{
            allSaved = Utils.executeQuery(
                    MovieInfo.class,
                    SavedActivity.this,
                    "select",
                    "*",
                    "movie_info",
                    "INNER JOIN",
                    String.format("saved_movies ON saved_movies.movie_id = movie_info.movie_id WHERE user_id = %s", currentUserId)
            );

            savedByUser = Utils.executeQuery(
                    SavedMovies.class,
                    SavedActivity.this,
                    "select",
                    "*",
                    "saved_movies",
                    "where",
                    String.format("user_id = %s", currentUserId)
            );

        } catch (ExecutionException e) {
            Toast.makeText(SavedActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(SavedActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        LinkedList<SavedMovies> savedList = new LinkedList<>(savedByUser);
        LinkedList<MovieInfo> moviesSaved = new LinkedList<>(allSaved);
        RecyclerView mRecyclerView = findViewById(R.id.rv_saved);
        MovieAdapter mAdapter = new MovieAdapter(this, moviesSaved, savedList, currentUserId);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
