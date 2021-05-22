package com.example.happy.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chaquo.python.PyException;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.happy.R;
import com.example.happy.adapters.MovieAdapter;
import com.example.happy.adapters.RankAdapter;
import com.example.happy.data.Movie;
import com.example.happy.data.MovieDatabase;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.MovieRatings;
import com.example.happy.queries.NoResult;
import com.example.happy.queries.UserRatings;
import com.example.happy.queries.Users;
import com.example.happy.queries.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Top12Activity extends AppCompatActivity {

    private LinkedList<MovieInfo> movieList = new LinkedList<>();
    private RecyclerView recyclerView;
    private MovieAdapter mAdapter;
    volatile List<MovieInfo> movieRecommendations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top12);
        View loadingIcon = findViewById(R.id.loadingPanel);

        //List<MovieInfo> movieRecommendations = null;

        // Get a handle to the RecyclerView
        recyclerView = findViewById(R.id.rv_top12);
        // Create an adapter and supply the data to be displayed
        mAdapter = new MovieAdapter(this, movieList);


        // Retrieve the current user of the app
        int currentUserId = -1;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("CURRENT_USER_ID", -1);
        }

        try {
            // Get the movies rated by the user
            List<UserRatings> currentUserRatings = Utils.executeQuery(
                    UserRatings.class,
                    Top12Activity.this,
                    "select",
                    "*",
                    "user_ratings",
                    "where",
                    String.format("user_id=%s", 0)
            );

            // Get the info of every movie
            List<MovieRatings> movieInfoTable = Utils.executeQuery(
                    MovieRatings.class,
                    Top12Activity.this,
                    "select",
                    "*",
                    "movie_ratings",
                    "",
                    ""
            );

            String userRatingsJson = new Gson().toJson(currentUserRatings);
            String movieInfoJson = new Gson().toJson(movieInfoTable);

            // Now we call the python script that will return a string with ordered movie ids
            // We create a new thread because otherwise the app "isn't responding" but actually
            // it is just taking long.
            Thread executeScript = new Thread(new Runnable() {
                public void run() {

                    // start python
                    if (!Python.isStarted()) {
                        Python.start(new AndroidPlatform(Top12Activity.this));
                    }

                    // create new python instance
                    Python py = Python.getInstance();
                    PyObject pyObj = py.getModule("recommendation");

                    PyObject obj = pyObj.callAttr("get_user_recommendations", movieInfoJson, userRatingsJson);

                    String recomOutput = obj.toString();
                    String recomIds = "(" + recomOutput.split("]")[0].substring(2) + ")";


                    // Get the info of every movie
                    try {
                        movieRecommendations = Utils.executeQuery(
                                MovieInfo.class,
                                Top12Activity.this,
                                "select",
                                "*",
                                "movie_info",
                                "where",
                                String.format("movie_id in %s", recomIds)
                        );
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (movieRecommendations != null) {
                        for (MovieInfo movie : movieRecommendations){
                            movieList.add(movie);
                        }
                    }

                    Top12Activity.this.runOnUiThread(
                            new Runnable() {
                                public void run() {
                                    loadingIcon.setVisibility(View.GONE);
                                    mAdapter.notifyDataSetChanged();
                                }});
                }
            });

            executeScript.start();

        } catch (ExecutionException e) {
            Toast.makeText(Top12Activity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(Top12Activity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (PyException e) {
            //Toast.makeText(Top12Activity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        // Connect the adapter with the RecyclerView
        recyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void launchRateActivity(View view) {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
    }
}
