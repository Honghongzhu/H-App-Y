package com.example.happy.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.adapters.MovieAdapter;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SavedActivity extends AppCompatActivity {
    private List<MovieInfo> allSaved;

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

            // check for the right columns, do we need to change this?

           // Toast.makeText(SavedActivity.this, allSaved.get(0).getMovieId(), Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(SavedActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(SavedActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        LinkedList<MovieInfo> moviesSaved = new LinkedList<>(allSaved);
        RecyclerView mRecyclerView = findViewById(R.id.rv_saved);
        MovieAdapter mAdapter = new MovieAdapter(this, moviesSaved, currentUserId);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

//    public void launchRateActivity(View view) {
//        Intent intent = new Intent(this, RateActivity.class);
//        startActivity(intent);
//    }
}
