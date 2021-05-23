package com.example.happy.screens;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.happy.R;
import com.example.happy.adapters.MovieAdapter;
import com.example.happy.queries.MovieInfo;
import com.example.happy.queries.MovieRatings;
import com.example.happy.queries.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class RecommendationActivity extends AppCompatActivity {

    //private LinkedList<MovieInfo> moviesToAdapt;
    private List<MovieInfo> allMovieInfo;
    private List<MovieRatings> resultMovieRatings;
    private List<MovieRatings> top12Movies;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        ArrayList<String> chosenCS = (ArrayList<String>) getIntent().getSerializableExtra("chosenCS");
        String listCS = String.join(", ", chosenCS);
        String listSumCS = String.join(" + ", chosenCS);

        //Toast.makeText(RecommendationActivity.this, listCS, Toast.LENGTH_LONG).show();

        // Query all movieRatings with the requested CS of the user
        try {
            resultMovieRatings = Utils.executeQuery(
                    MovieRatings.class,
                    RecommendationActivity.this,
                    "select",
                    String.format("movie_id, average_enjoyment, average_meaning, %s, SUM(%s)", listCS, listSumCS),
                    "movie_ratings",
                    "GROUP BY",
                    String.format("movie_id HAVING SUM(%s) >0 ORDER BY SUM(%s) DESC, average_meaning DESC, average_enjoyment DESC", listSumCS, listSumCS)
            );
        } catch (ExecutionException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        // Get the first 12 movies
        top12Movies = resultMovieRatings.stream().limit(12).collect(Collectors.toList());

        Toast.makeText(RecommendationActivity.this, top12Movies.get(0).getMovieId() + " " + top12Movies.get(0).getAverageEnjoyment(), Toast.LENGTH_LONG).show();


        // Query all movieInfo
//        try {
//            allMovieInfo = Utils.executeQuery(
//                    MovieInfo.class,
//                    RecommendationActivity.this,
//                    "select",
//                    "*",
//                    "movie_info",
//                    "",
//                    ""
//            );
//        } catch (ExecutionException e) {
//            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//        } catch (InterruptedException e) {
//            Toast.makeText(RecommendationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//        }



//        recyclerView = findViewById(R.id.rv_recommendation);
//        adapter = new MovieAdapter(this, allMovieInfo);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void launchRateActivity(View view) {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
    }
}
